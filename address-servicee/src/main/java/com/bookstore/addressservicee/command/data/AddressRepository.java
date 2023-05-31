package com.bookstore.addressservicee.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    Address findByUserId(Long userId);

    List<Address> findByFirstNameContainingIgnoreCase(String firstName);

    List<Address> findByPhoneNumberContainingIgnoreCase(String phoneNumber);

    List<Address> findByProvinceId(String provinceId);
}
