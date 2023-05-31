package com.bookstore.provinceservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<Province, String> {

    List<Province> findByNameContainingIgnoreCase(String name);
}
