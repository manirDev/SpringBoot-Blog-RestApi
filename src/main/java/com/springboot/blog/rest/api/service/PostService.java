package com.springboot.blog.rest.api.service;

import com.springboot.blog.rest.api.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getAllPosts();
    PostDto getPostById(Long postId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);
}
