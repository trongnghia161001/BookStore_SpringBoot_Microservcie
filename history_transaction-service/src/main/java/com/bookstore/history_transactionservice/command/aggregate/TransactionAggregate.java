package com.bookstore.history_transactionservice.command.aggregate;

import com.bookstore.commonservice.command.CreateTransactionByBillCommand;
import com.bookstore.commonservice.event.TransactionCreateByBillEvent;
import com.bookstore.history_transactionservice.command.command.CreateTransactionCommand;
import com.bookstore.history_transactionservice.command.command.DeleteTransactionCommand;
import com.bookstore.history_transactionservice.command.command.UpdateTransactionCommand;
import com.bookstore.history_transactionservice.command.events.TransactionCreateEvent;
import com.bookstore.history_transactionservice.command.events.TransactionDeleteEvent;
import com.bookstore.history_transactionservice.command.events.TransactionUpdateEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class TransactionAggregate {

    @AggregateIdentifier
    private String id;

    private String name;

    private Double price;

    private String avatar;

    private int quantity;

    private Double total;

    private String billId;

    private Long userId;

    private String shoppingCartId;

    @CommandHandler
    public TransactionAggregate(CreateTransactionByBillCommand command) {
        TransactionCreateByBillEvent event = new TransactionCreateByBillEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(TransactionCreateByBillEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.price = event.getPrice();
        this.avatar = event.getAvatar();
        this.quantity = event.getQuantity();
        this.total = event.getTotal();
        this.billId = event.getBillId();
        this.userId = event.getUserId();
        this.shoppingCartId = event.getShoppingCartId();
    }

    @CommandHandler
    public void handler(UpdateTransactionCommand command) {
        TransactionUpdateEvent event = new TransactionUpdateEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(TransactionUpdateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.price = event.getPrice();
        this.avatar = event.getAvatar();
        this.quantity = event.getQuantity();
        this.total = event.getTotal();
        this.billId = event.getBillId();
        this.userId = event.getUserId();
        this.shoppingCartId = event.getShoppingCartId();
    }

    @CommandHandler
    public void handler(DeleteTransactionCommand command) {
        TransactionDeleteEvent event = new TransactionDeleteEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(TransactionDeleteEvent event) {
        this.id = event.getId();
    }




}
