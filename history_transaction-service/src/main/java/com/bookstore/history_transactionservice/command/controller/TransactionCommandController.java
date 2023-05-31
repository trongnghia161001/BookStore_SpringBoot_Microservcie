package com.bookstore.history_transactionservice.command.controller;


import com.bookstore.history_transactionservice.command.command.CreateTransactionCommand;
import com.bookstore.history_transactionservice.command.command.DeleteTransactionCommand;
import com.bookstore.history_transactionservice.command.command.UpdateTransactionCommand;
import com.bookstore.history_transactionservice.command.model.TransactionRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionCommandController {

    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addAdmin(@RequestBody TransactionRequestModel model) {
        CreateTransactionCommand command = new CreateTransactionCommand();
        BeanUtils.copyProperties(model, command);
        command.setId(UUID.randomUUID().toString());
        commandGateway.sendAndWait(command);
        return "Add";
    }

    @PutMapping
    public String updateBook(@RequestBody TransactionRequestModel model) {
        UpdateTransactionCommand command = new UpdateTransactionCommand();
        BeanUtils.copyProperties(model, command);
        commandGateway.sendAndWait(command);
        return "Update";
    }

    @DeleteMapping("/{transactionId}")
    public String deleteBook(@PathVariable String transactionId) {
        DeleteTransactionCommand command = new DeleteTransactionCommand(transactionId);
        commandGateway.sendAndWait(command);
        return "Delete";
    }
}
