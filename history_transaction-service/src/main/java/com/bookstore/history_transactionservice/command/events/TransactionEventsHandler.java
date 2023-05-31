package com.bookstore.history_transactionservice.command.events;

import com.bookstore.commonservice.event.TransactionCreateByBillEvent;
import com.bookstore.history_transactionservice.command.data.Transaction;
import com.bookstore.history_transactionservice.command.data.TransactionRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventsHandler {

    @Autowired
    private TransactionRepository adminRepository;

    @EventHandler
    public void on(TransactionCreateEvent event) {
        Transaction admin = new Transaction();
        BeanUtils.copyProperties(event, admin);
        adminRepository.save(admin);
    }

    @EventHandler
    public void on(TransactionUpdateEvent event) {
        Transaction admin = adminRepository.getReferenceById(event.getId());
        BeanUtils.copyProperties(event, admin);
        adminRepository.save(admin);
    }

    @EventHandler
    public void on(TransactionDeleteEvent event) {
        adminRepository.deleteById(event.getId());
    }

    @EventHandler
    public void on(TransactionCreateByBillEvent event) {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(event, transaction);
        adminRepository.save(transaction);
    }
}
