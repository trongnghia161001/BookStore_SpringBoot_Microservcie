package com.bookstore.billservice.query.controller;

import com.bookstore.billservice.query.model.BillResponseModel;
import com.bookstore.billservice.query.queries.*;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bill")
public class BillQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<BillResponseModel> getAll() {
        GetAllBillQuery getAllBookQuery = new GetAllBillQuery();
        List<BillResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(BillResponseModel.class)).join();
        return list;
    }

    @GetMapping("/{id}")
    public BillResponseModel getAllBillById(@PathVariable String id) {
        GetAllBillByIdQuery getAllBillByIdQuery = new GetAllBillByIdQuery(id);
        BillResponseModel billResponseModels = queryGateway.query(getAllBillByIdQuery,
                ResponseTypes.instanceOf(BillResponseModel.class)).join();
        return billResponseModels;
    }

    @GetMapping("/userName")
    public List<BillResponseModel> getAllBillByUserName(@RequestParam(required = false) String name) {
        GetAllBillByUserNameQuery getAllBillByUserNameQuery = new GetAllBillByUserNameQuery(name);
        List<BillResponseModel> billResponseModels = queryGateway.query(getAllBillByUserNameQuery,
                ResponseTypes.multipleInstancesOf(BillResponseModel.class)).join();
        return billResponseModels;
    }

    @GetMapping("/phoneNumber")
    public List<BillResponseModel> getAllBillByPhone(@RequestParam(required = false) String phoneNumber) {
        GetAllBillByPhoneQuery getAllBillByPhoneQuery = new GetAllBillByPhoneQuery(phoneNumber);
        List<BillResponseModel> billResponseModels = queryGateway.query(getAllBillByPhoneQuery,
                ResponseTypes.multipleInstancesOf(BillResponseModel.class)).join();
        return billResponseModels;
    }

    @GetMapping("/status")
    public List<BillResponseModel> getAllBillByStatus(@RequestParam(required = false) String status) {
        GetAllBillByStatusQuery getAllBillByStatusQuery = new GetAllBillByStatusQuery(status);
        List<BillResponseModel> billResponseModels = queryGateway.query(getAllBillByStatusQuery,
                ResponseTypes.multipleInstancesOf(BillResponseModel.class)).join();
        return billResponseModels;
    }
}


//tim kiem: ten khach hang, so dien thoai, status