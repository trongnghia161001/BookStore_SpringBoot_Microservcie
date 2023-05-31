package com.bookstore.commentsservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, String> {

    List<Comments> findByUserIdIsNotNull(Long userId);

    List<Comments> findByContentContainingIgnoreCase(String content);
}
