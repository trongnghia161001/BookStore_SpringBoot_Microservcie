package com.bookstore.authorservice.query.controller;

import com.bookstore.authorservice.query.model.AuthorResponseModel;
import com.bookstore.authorservice.query.queries.GetAllAuthorByNameQuery;
import com.bookstore.authorservice.query.queries.GetAllAuthorQuery;
import com.bookstore.commonservice.model.ProductResponseCommonModel;
import com.bookstore.commonservice.query.GetProductByAuthorQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<AuthorResponseModel> getAllAuthor() {
        GetAllAuthorQuery getAllBookQuery = new GetAllAuthorQuery();
        List<AuthorResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(AuthorResponseModel.class)).join();
        return list;
    }


    @GetMapping("/authorName")
    public List<AuthorResponseModel> getAllAuthorByAuthorName(@RequestParam(required = false) String authorName) {
        GetAllAuthorByNameQuery authorQuery = new GetAllAuthorByNameQuery(authorName);
        List<AuthorResponseModel> authorResponseModels = queryGateway.query(authorQuery,
                ResponseTypes.multipleInstancesOf(AuthorResponseModel.class)).join();
        return authorResponseModels;
    }

    @GetMapping("/products/authorName")
    public List<ProductResponseCommonModel> getAllProductByAuthor(@RequestParam(required = false) String authorName) {
        GetAllAuthorByNameQuery authorQuery = new GetAllAuthorByNameQuery(authorName);
        List<ProductResponseCommonModel> list = queryGateway.query(authorQuery,
                ResponseTypes.multipleInstancesOf(ProductResponseCommonModel.class)).join();
        return list;
    }
}

//tim kiem: name