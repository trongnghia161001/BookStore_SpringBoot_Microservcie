package com.bookstore.carrierservice.query.controller;

import com.bookstore.carrierservice.query.model.CarrierResponseModel;
import com.bookstore.carrierservice.query.queries.GetAllCarrierByAddressQuery;
import com.bookstore.carrierservice.query.queries.GetAllCarrierByNameQuery;
import com.bookstore.carrierservice.query.queries.GetAllCarrierByPhoneQuery;
import com.bookstore.carrierservice.query.queries.GetAllCarrierQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carrier")
public class CarrierQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<CarrierResponseModel> getAll() {
        GetAllCarrierQuery getAllBookQuery = new GetAllCarrierQuery();
        List<CarrierResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(CarrierResponseModel.class)).join();
        return list;
    }

    @GetMapping("/name")
    public List<CarrierResponseModel> getAllByName(@RequestParam(required = false) String name) {
        GetAllCarrierByNameQuery getAllCarrierByNameQuery = new GetAllCarrierByNameQuery(name);
        List<CarrierResponseModel> list = queryGateway.query(getAllCarrierByNameQuery,
                ResponseTypes.multipleInstancesOf(CarrierResponseModel.class)).join();
        return list;
    }

    @GetMapping("/phone")
    public List<CarrierResponseModel> getAllByPhone(@RequestParam(required = false) String phone) {
        GetAllCarrierByPhoneQuery getAllCarrierByPhoneQuery = new GetAllCarrierByPhoneQuery(phone);
        List<CarrierResponseModel> list = queryGateway.query(getAllCarrierByPhoneQuery,
                ResponseTypes.multipleInstancesOf(CarrierResponseModel.class)).join();
        return list;
    }

    @GetMapping("/address")
    public List<CarrierResponseModel> getAllByAddress(@RequestParam(required = false) String address) {
        GetAllCarrierByAddressQuery getAllCarrierByAddressQuery = new GetAllCarrierByAddressQuery(address);
        List<CarrierResponseModel> list = queryGateway.query(getAllCarrierByAddressQuery,
                ResponseTypes.multipleInstancesOf(CarrierResponseModel.class)).join();
        return list;
    }

}

//tim kiem: name, phone, address
