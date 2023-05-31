package com.bookstore.categoriesservice.query.controller;

import com.bookstore.categoriesservice.query.model.CategoriesResponseModel;
import com.bookstore.categoriesservice.query.queries.GetAllCategoriesByDesQuery;
import com.bookstore.categoriesservice.query.queries.GetAllCategoriesByNameQuery;
import com.bookstore.categoriesservice.query.queries.GetAllCategoriesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<CategoriesResponseModel> getAll() {
        GetAllCategoriesQuery getAllBookQuery = new GetAllCategoriesQuery();
        List<CategoriesResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(CategoriesResponseModel.class)).join();
        return list;
    }

    @GetMapping("/name")
    public List<CategoriesResponseModel> getAllByName(@RequestParam(required = false) String name) {
        GetAllCategoriesByNameQuery getAllCategoriesByNameQuery = new GetAllCategoriesByNameQuery(name);
        List<CategoriesResponseModel> list = queryGateway.query(getAllCategoriesByNameQuery,
                ResponseTypes.multipleInstancesOf(CategoriesResponseModel.class)).join();
        return list;
    }

    @GetMapping("/description")
    public List<CategoriesResponseModel> getAllByDes(@RequestParam(required = false) String description) {
        GetAllCategoriesByDesQuery getAllCategoriesByDesQuery = new GetAllCategoriesByDesQuery(description);
        List<CategoriesResponseModel> list = queryGateway.query(getAllCategoriesByDesQuery,
                ResponseTypes.multipleInstancesOf(CategoriesResponseModel.class)).join();
        return list;
    }
}

//tim kiem: name, des