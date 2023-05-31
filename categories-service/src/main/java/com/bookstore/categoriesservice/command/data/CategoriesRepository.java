package com.bookstore.categoriesservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, String> {

    List<Categories> findByNameContainingIgnoreCase(String name);

//    List<Categories> findByDescriptionContainingIgnoreCase(String description);


}
