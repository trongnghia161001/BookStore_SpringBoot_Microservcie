package com.bookstore.payment_methodservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, String> {

    Optional<PaymentMethod> findById(String id);
}
