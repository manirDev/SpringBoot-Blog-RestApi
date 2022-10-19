package com.springboot.blog.rest.api.repository;

import com.springboot.blog.rest.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
