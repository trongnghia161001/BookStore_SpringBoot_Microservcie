package com.bookstore.history_transactionservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByBillIdAndUserId(String billId, Long userId);
}
