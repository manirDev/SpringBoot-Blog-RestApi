package com.springboot.blog.rest.api.repository;

import com.springboot.blog.rest.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p where " +
            "p.title LIKE CONCAT('%', :query, '%')" +
            "Or p.description LIKE CONCAT('%', :query, '%')")
    List<Post> searchPost(String query);

}
