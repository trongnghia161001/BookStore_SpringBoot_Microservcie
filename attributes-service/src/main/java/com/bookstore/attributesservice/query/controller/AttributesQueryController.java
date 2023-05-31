package com.bookstore.attributesservice.query.controller;

import com.bookstore.attributesservice.query.model.AttributesResponseModel;
import com.bookstore.attributesservice.query.queries.GetAllAttributesByCategoryQuery;
import com.bookstore.attributesservice.query.queries.GetAllAttributesByNameQuery;
import com.bookstore.attributesservice.query.queries.GetAllAttributesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attributes")
public class AttributesQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<AttributesResponseModel> getAll() {
        GetAllAttributesQuery getAllBookQuery = new GetAllAttributesQuery();
        List<AttributesResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(AttributesResponseModel.class)).join();
        return list;
    }

    @GetMapping("/name")
    public List<AttributesResponseModel> getAllByName(@RequestParam(required = false) String name) {
        GetAllAttributesByNameQuery getAllBookQuery = new GetAllAttributesByNameQuery(name);
        List<AttributesResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(AttributesResponseModel.class)).join();
        return list;
    }

    @GetMapping("/categories")
    public List<AttributesResponseModel> getAllByCategory(@RequestParam(required = false) String category) {
        GetAllAttributesByCategoryQuery getAllBookQuery = new GetAllAttributesByCategoryQuery(category);
        List<AttributesResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(AttributesResponseModel.class)).join();
        return list;
    }
}

//tim kiem: name, category

