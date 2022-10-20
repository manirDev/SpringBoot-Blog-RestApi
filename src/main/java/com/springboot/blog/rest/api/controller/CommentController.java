package com.springboot.blog.rest.api.controller;

import com.springboot.blog.rest.api.dto.CommentDto;
import com.springboot.blog.rest.api.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //create comment for post api
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Long postId){
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    //get comments by post id api
    @GetMapping("/{postId}/getComments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable Long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    //get comment by post id and comment id
    @GetMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId,
                                                     @PathVariable Long commentId){

        CommentDto responseComment = commentService.getCommentByID(postId, commentId);
        return new ResponseEntity<>(responseComment, HttpStatus.OK);
    }

    //update comment
    @PutMapping("/{postId}/updateComment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @RequestBody CommentDto commentDto,
                                                    @PathVariable Long commentId){
        CommentDto updatedComment = commentService.updateComment(postId, commentDto, commentId);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    //delete comment
    @DeleteMapping("/{postId}/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}