package com.bookstore.carrierservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrierRepository extends JpaRepository<Carrier, String> {

    List<Carrier> findByNameContainingIgnoreCase(String name);

    List<Carrier> findByPhoneContainingIgnoreCase(String phone);

    List<Carrier> findByAddressContainingIgnoreCase(String address);
}
