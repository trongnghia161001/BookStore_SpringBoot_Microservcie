package com.bookstore.billservice.query.projection;

import com.bookstore.billservice.command.data.Bill;
import com.bookstore.billservice.command.data.BillRepository;
import com.bookstore.billservice.query.model.BillResponseModel;
import com.bookstore.billservice.query.model.TransactionResponseModel;
import com.bookstore.billservice.query.queries.*;
import com.bookstore.commonservice.model.*;
import com.bookstore.commonservice.query.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BillProjection {

    @Autowired
    private BillRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<BillResponseModel> getAllBill(GetAllBillQuery getAll) {
        List<BillResponseModel> list = new ArrayList<>();
        List<Bill> List = repository.findAll();
        List.forEach(book -> {
            BillResponseModel billResponseModel = new BillResponseModel();
            GetDetailsUserQuery getDetailsEmployeeQuery = new GetDetailsUserQuery(book.getUserId());
            UserResponseCommonModel userResponseCommonModel =
                    queryGateway.query(getDetailsEmployeeQuery,
                                    ResponseTypes.instanceOf(UserResponseCommonModel.class))
                            .join();
            BeanUtils.copyProperties(book, billResponseModel);
            billResponseModel.setUsername(userResponseCommonModel.getUsername());
            list.add(billResponseModel);
        });
        return list;
    }

    @QueryHandler
    public BillResponseModel getAllBillById(GetAllBillByIdQuery getAll) {
        BillResponseModel model = new BillResponseModel();
        Bill bill = repository.getReferenceById(getAll.getId());
        if (bill != null) {
            GetDetailsUserQuery getDetailsEmployeeQuery = new GetDetailsUserQuery(bill.getUserId());
            GetAllTransactionByBillQuery getAllTransactionByBillQuery = new GetAllTransactionByBillQuery(
                    bill.getId(), bill.getUserId()
            );
            List<TransactionByBillResponseCommonModel> transaction =
                    queryGateway.query(getAllTransactionByBillQuery, ResponseTypes.multipleInstancesOf(
                            TransactionByBillResponseCommonModel.class
                    )).join();
            UserResponseCommonModel userResponseCommonModel =
                    queryGateway.query(getDetailsEmployeeQuery,
                                    ResponseTypes.instanceOf(UserResponseCommonModel.class))
                            .join();
            BeanUtils.copyProperties(bill, model);
            List<TransactionResponseModel> transactionResponseModels = new ArrayList<>();
            for (TransactionByBillResponseCommonModel response : transaction) {
                TransactionResponseModel transactionResponseModel = new TransactionResponseModel();
                BeanUtils.copyProperties(response, transactionResponseModel);
                transactionResponseModels.add(transactionResponseModel);
            }
            model.setUsername(userResponseCommonModel.getUsername());
            model.setTransactionResponseModels(transactionResponseModels);
        }
        return model;
    }

    @QueryHandler
    public List<BillResponseModel> getAllBillByUserName(GetAllBillByUserNameQuery getAll) {
        List<BillResponseModel> list = new ArrayList<>();
        List<Bill> List = repository.findByNameContainingIgnoreCase(getAll.getName());
        List.forEach(book -> {
            BillResponseModel billResponseModel = new BillResponseModel();
            BeanUtils.copyProperties(book, billResponseModel);
            list.add(billResponseModel);
        });
        return list;
    }

    @QueryHandler
    public List<BillResponseModel> getAllBillByPhone(GetAllBillByPhoneQuery getAll) {
        List<BillResponseModel> list = new ArrayList<>();
        List<Bill> List = repository.findByPhoneContainingIgnoreCase(getAll.getPhone());
        List.forEach(book -> {
            BillResponseModel billResponseModel = new BillResponseModel();
            BeanUtils.copyProperties(book, billResponseModel);
            list.add(billResponseModel);
        });
        return list;
    }

    @QueryHandler
    public List<BillResponseModel> getAllBillByStatus(GetAllBillByStatusQuery getAll) {
        List<BillResponseModel> list = new ArrayList<>();
        List<Bill> List = repository.findByStatusContainingIgnoreCase(getAll.getStatus());
        List.forEach(book -> {
            BillResponseModel billResponseModel = new BillResponseModel();
            BeanUtils.copyProperties(book, billResponseModel);
            list.add(billResponseModel);
        });
        return list;
    }

    @QueryHandler
    public BillResponseCommonModel getBillById(GetDetailsBillQuery query) {
        BillResponseCommonModel model = new BillResponseCommonModel();
        Bill bill= repository.findByIdAndUserId(query.getBillId(), query.getUserId());
        if (bill != null) {
            BeanUtils.copyProperties(bill, model);
        }
        return model;
    }


}
