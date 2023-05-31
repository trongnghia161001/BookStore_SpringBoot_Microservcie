package com.bookstore.attributesservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttributesRepository extends JpaRepository<Attributes, String> {

    List<Attributes> findByNameContainingIgnoreCase(String name);

    List<Attributes> findByCategoryId(String categoriesId);
}
