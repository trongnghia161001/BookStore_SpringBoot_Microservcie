package com.bookstore.adminsservice.query.controller;

import com.bookstore.adminsservice.query.model.AdminsResponseModel;
import com.bookstore.adminsservice.query.queries.GetAdminByIdQuery;
import com.bookstore.adminsservice.query.queries.GetAllAdminsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminsQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<AdminsResponseModel> getAll() {
        GetAllAdminsQuery getAllBookQuery = new GetAllAdminsQuery();
        List<AdminsResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(AdminsResponseModel.class)).join();
        return list;
    }

    @GetMapping("/adminId")
    public AdminsResponseModel getAllById(@RequestParam(required = false) String adminId) {
        GetAdminByIdQuery getAdminByIdQuery = new GetAdminByIdQuery(adminId);
        AdminsResponseModel list = queryGateway.query(getAdminByIdQuery,
                ResponseTypes.instanceOf(AdminsResponseModel.class)).join();
        return list;
    }


}
