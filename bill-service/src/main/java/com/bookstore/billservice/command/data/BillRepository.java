package com.bookstore.billservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, String> {
    Bill findByIdAndUserId(String id, Long userId);

    void deleteByIdAndUserId(String id, Long userId);

    List<Bill> findByNameContainingIgnoreCase(String name);

    List<Bill> findByPhoneContainingIgnoreCase(String phone);

    List<Bill> findByStatusContainingIgnoreCase(String status);



}
