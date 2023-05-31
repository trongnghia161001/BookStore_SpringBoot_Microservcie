package com.bookstore.history_transactionservice.query.controller;

import com.bookstore.history_transactionservice.query.model.TransactionResponseModel;
import com.bookstore.history_transactionservice.query.queries.GetAllTransactionQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public List<TransactionResponseModel> getAllTransaction() {
        GetAllTransactionQuery getAllBookQuery = new GetAllTransactionQuery();
        List<TransactionResponseModel> list = queryGateway.query(getAllBookQuery,
                ResponseTypes.multipleInstancesOf(TransactionResponseModel.class)).join();
        return list;
    }

    
}
