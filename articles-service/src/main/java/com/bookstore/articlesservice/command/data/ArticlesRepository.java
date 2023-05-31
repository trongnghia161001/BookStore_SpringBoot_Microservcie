package com.bookstore.articlesservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticlesRepository extends JpaRepository<Articles, String> {

    List<Articles> findByNameContainingIgnoreCase(String name);

    List<Articles> findByDescriptionContainingIgnoreCase(String description);
}
