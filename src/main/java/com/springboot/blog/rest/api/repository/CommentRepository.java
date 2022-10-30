package com.springboot.blog.rest.api.repository;

import com.springboot.blog.rest.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

    @Query("SELECT c FROM Comment c WHERE " +
            "c.name LIKE CONCAT('%', :query, '%') " +
            "Or c.body LIKE CONCAT('%', :query, '%') ")
    List<Comment> searchComment(String query);
}
