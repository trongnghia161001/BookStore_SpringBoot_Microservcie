package com.bookstore.addressservicee.query.controller;

import com.bookstore.addressservicee.query.model.AddressResponseModel;
import com.bookstore.addressservicee.query.queries.GetAllAddressByFirstnameQuery;
import com.bookstore.addressservicee.query.queries.GetAllAddressByPhoneQuery;
import com.bookstore.addressservicee.query.queries.GetAllAddressByProvinceQuery;
import com.bookstore.addressservicee.query.queries.GetAllAddressQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<AddressResponseModel> getAllAddress() {
        GetAllAddressQuery getAllBookQuery = new GetAllAddressQuery();
        List<AddressResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(AddressResponseModel.class)).join();
        return list;
    }

    @GetMapping("/firstName")
    public List<AddressResponseModel> getAllAddressByFistName(@RequestParam(required = false) String firstName) {
        GetAllAddressByFirstnameQuery getAllAddressByLastnameQuery = new GetAllAddressByFirstnameQuery(firstName);
        List<AddressResponseModel> list = queryGateway.query(getAllAddressByLastnameQuery,
                ResponseTypes.multipleInstancesOf(AddressResponseModel.class)).join();
        return list;
    }

    @GetMapping("/phoneNumber")
    public List<AddressResponseModel> getAllAddressByPhone(@RequestParam(required = false) String phoneNumber) {
        GetAllAddressByPhoneQuery getAllAddressByPhoneQuery = new GetAllAddressByPhoneQuery(phoneNumber);
        List<AddressResponseModel> list = queryGateway.query(getAllAddressByPhoneQuery,
                ResponseTypes.multipleInstancesOf(AddressResponseModel.class)).join();
        return list;
    }

    @GetMapping("/provinceName")
    public List<AddressResponseModel> getAllAddressByProvince(@RequestParam(required = false) String provinceName) {
        GetAllAddressByProvinceQuery getAllAddressByProvinceQuery = new GetAllAddressByProvinceQuery(provinceName);
        List<AddressResponseModel> list = queryGateway.query(getAllAddressByProvinceQuery,
                ResponseTypes.multipleInstancesOf(AddressResponseModel.class)).join();
        return list;
    }

}

//tim kiem: fistName, phone, province
