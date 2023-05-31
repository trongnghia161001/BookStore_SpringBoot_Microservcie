package com.bookstore.billservice.command.saga;

import com.bookstore.billservice.command.command.DeleteBillCommand;
import com.bookstore.billservice.command.events.BillCreateEvent;
import com.bookstore.billservice.command.events.BillDeleteEvent;
import com.bookstore.commonservice.command.CreateTransactionByBillCommand;
import com.bookstore.commonservice.command.RollBackStatusBillCommand;
import com.bookstore.commonservice.command.UpdateStatusBillCommand;
import com.bookstore.commonservice.event.BillRollBackStatusEvent;
import com.bookstore.commonservice.event.BillUpdateStatusEvent;
import com.bookstore.commonservice.model.*;
import com.bookstore.commonservice.query.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Saga
public class BillSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BillCreateEvent event) {
        System.out.println("BillCreatedEvent in Saga for Bill: " + event.getId());
        try {
            SagaLifecycle.associateWith("id", event.getId());
            GetDetailsUserQuery getDetailsEmployeeQuery = new GetDetailsUserQuery(event.getUserId());
            UserResponseCommonModel employeeResponseCommonModel =
                    queryGateway.query(getDetailsEmployeeQuery,
                                    ResponseTypes.instanceOf(UserResponseCommonModel.class))
                            .join();
            if (employeeResponseCommonModel != null) {
                if (employeeResponseCommonModel.getEnabled() == true) {
                    throw new Exception("Khach hang bi ky luat");
                } else {
                    GetDetailsAddressQuery getDetailsAddressQuery = new GetDetailsAddressQuery(event.getUserId());
                    AddressResponseCommonModel address = queryGateway.query(getDetailsAddressQuery,
                            ResponseTypes.instanceOf(AddressResponseCommonModel.class)).join();
                    if (address != null) {
                        if (address.getId() != null && !address.getId().isEmpty()
                                && address.getUserId() != null
                                && address.getFirstName() != null && !address.getFirstName().isEmpty()
                                && address.getLastName() != null && !address.getLastName().isEmpty()
                                && address.getPhoneNumber() != null && !address.getPhoneNumber().isEmpty()
                                && address.getAddressLine1() != null && !address.getAddressLine1().isEmpty()
                                && address.getProvinceId() != null && !address.getProvinceId().isEmpty()) {
                            GetDetailsProvinceQuery getDetailsProvinceQuery = new GetDetailsProvinceQuery(address.getProvinceId());
                            ProvinceResponseCommonModel province = queryGateway.query(getDetailsProvinceQuery,
                                    ResponseTypes.instanceOf(ProvinceResponseCommonModel.class)).join();
                           if (province != null) {
                               UpdateStatusBillCommand command = new UpdateStatusBillCommand();
                               BeanUtils.copyProperties(event, command);
                               command.setName(address.getLastName() + " " + address.getFirstName());
                               command.setPhone(address.getPhoneNumber());
                               command.setAddress(address.getAddressLine1() + "," + province.getName());
                               command.setCreated_at(new Date());
                               command.setStatus("Dang cho thanh toan");
                               commandGateway.sendAndWait(command);
                           }
                        }
                        else {
                            throw new Exception("Khach hang chua dien du thong tin nhan hang");
                        }
                    } else {
                        throw new Exception("Khach hang chua dien thong tin nhan hang");
                    }
                }
            } else {
                throw new Exception("Khong ton tai user: " + employeeResponseCommonModel.getLastName() + " " +
                        employeeResponseCommonModel.getFirstName());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rollBackBillStatus(event.getId(), event.getUserId());
        }
    }

    @SagaEventHandler(associationProperty = "id")
    private void handle(BillUpdateStatusEvent event) {
        System.out.println("BillUpdateStatusEvent in Saga for billId : " + event.getId());

        try {
            CompletableFuture<List<ShoppingCartResponseCommonModel>> shoppingCartFuture = queryGateway.query(
                    new GetShoppingCartByUserQuery(event.getUserId()),
                    ResponseTypes.multipleInstancesOf(ShoppingCartResponseCommonModel.class)
            );

            CompletableFuture<ShippingResponseCommonModel> shippingFuture = queryGateway.query(
                    new GetDetailsShippingQuery(event.getUserId()),
                    ResponseTypes.instanceOf(ShippingResponseCommonModel.class)
            );

            CompletableFuture.allOf(shoppingCartFuture, shippingFuture).join();

            List<ShoppingCartResponseCommonModel> cartList = shoppingCartFuture.join();
            ShippingResponseCommonModel shippingResponse = shippingFuture.join();

            if (!cartList.isEmpty() && shippingResponse != null) {
                List<Double> prices = cartList.stream()
                        .map(ShoppingCartResponseCommonModel::getTotalPrice)
                        .collect(Collectors.toList());

                double productCost = prices.stream().mapToDouble(Double::doubleValue).sum();
                double shippingCost = shippingResponse.getShipmentCost();
                double total = productCost + shippingCost;

                List<CompletableFuture<ProductResponseCommonModel>> productFutures = cartList.stream()
                        .map(cart -> queryGateway.query(
                                new GetDetailsProductQuery(cart.getProductId()),
                                ResponseTypes.instanceOf(ProductResponseCommonModel.class)
                        ))
                        .collect(Collectors.toList());

                CompletableFuture.allOf(productFutures.toArray(new CompletableFuture[0])).join();

                List<ProductResponseCommonModel> productList = productFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());

                List<CreateTransactionByBillCommand> transactionCommands = new ArrayList<>();

                UpdateStatusBillCommand command = new UpdateStatusBillCommand();
                BeanUtils.copyProperties(event, command);
                command.setProductCost(productCost);
                command.setShippingCost(shippingCost);
                command.setTotalMoney(total);

                commandGateway.sendAndWait(command);

                for (int i = 0; i < cartList.size(); i++) {
                    ShoppingCartResponseCommonModel cart = cartList.get(i);
                    ProductResponseCommonModel product = productList.get(i);

                    CreateTransactionByBillCommand transaction = new CreateTransactionByBillCommand(
                            UUID.randomUUID().toString(), product.getName(), product.getPrice(),
                            product.getAvatar(), cart.getQuantity(), product.getPrice() * cart.getQuantity(),
                            event.getId(), event.getUserId(), cart.getId());
                    transactionCommands.add(transaction);
                }
                transactionCommands.forEach(commandGateway::sendAndWait);
                SagaLifecycle.end();
            } else {
                throw new Exception("Bạn chưa chọn sản phẩm để mua");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rollBackBillStatus(event.getId(), event.getUserId());
        }
    }

    private void rollBackBillStatus(String id, Long userId) {
        SagaLifecycle.associateWith("id", id);
        RollBackStatusBillCommand command = new RollBackStatusBillCommand(id, userId);
        commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "id")
    public void handleRollBackBookStatus(BillRollBackStatusEvent event) {
        System.out.println("ProductRollBackStatusEvent in Saga for product Id : {} " + event.getId());
    }


    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    public void handle(BillDeleteEvent event) {
        System.out.println("BorrowDeletedEvent in Saga for Borrowing Id : {} " +
                event.getId());
        SagaLifecycle.end();
    }
}
