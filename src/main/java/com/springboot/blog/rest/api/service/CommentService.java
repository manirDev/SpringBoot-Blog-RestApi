package com.springboot.blog.rest.api.service;

import com.springboot.blog.rest.api.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Long postId);
    List<CommentDto> getAllCommentsByPostId(Long postId);
    CommentDto getCommentByID(Long postId, Long commentId);
    CommentDto updateComment(Long postId, CommentDto commentDto, Long commentId);
    void deleteComment(Long postId, Long commentId);
}
