package com.springboot.blog.rest.api.service;

import com.springboot.blog.rest.api.dto.PostDto;
import com.springboot.blog.rest.api.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long postId);
    PostDto updatePost(PostDto postDto, Long postId);
    void deletePost(Long postId);

    List<PostDto> searchPost(String query);
}
