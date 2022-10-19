package com.springboot.blog.rest.api.service;

import com.springboot.blog.rest.api.dto.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
