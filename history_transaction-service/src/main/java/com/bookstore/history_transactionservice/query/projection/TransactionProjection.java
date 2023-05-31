package com.bookstore.history_transactionservice.query.projection;

import com.bookstore.commonservice.model.TransactionByBillResponseCommonModel;
import com.bookstore.commonservice.query.GetAllTransactionByBillQuery;
import com.bookstore.history_transactionservice.command.data.Transaction;
import com.bookstore.history_transactionservice.command.data.TransactionRepository;
import com.bookstore.history_transactionservice.query.model.TransactionResponseModel;
import com.bookstore.history_transactionservice.query.queries.GetAllTransactionQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionProjection {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private QueryGateway queryGateway;

    @QueryHandler
    public List<TransactionResponseModel> handler(GetAllTransactionQuery getAllBookQuery) {
        List<TransactionResponseModel> list = new ArrayList<>();
        List<Transaction> bookList = repository.findAll();
        bookList.forEach(book -> {
            TransactionResponseModel model = new TransactionResponseModel();
            BeanUtils.copyProperties(book, model);
            list.add(model);
        });
        return list;
    }

    @QueryHandler
    public List<TransactionByBillResponseCommonModel> handler(GetAllTransactionByBillQuery query) {
        List<TransactionByBillResponseCommonModel> list = new ArrayList<>();
        List<Transaction> transactions = repository.findByBillIdAndUserId(query.getBillId(), query.getUserId());
        transactions.forEach(item -> {
            TransactionByBillResponseCommonModel model = new TransactionByBillResponseCommonModel();
            BeanUtils.copyProperties(item, model);
            list.add(model);
        });
        return list;
    }
}
