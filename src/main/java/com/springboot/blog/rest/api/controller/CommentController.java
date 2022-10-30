package com.springboot.blog.rest.api.controller;

import com.springboot.blog.rest.api.dto.CommentDto;
import com.springboot.blog.rest.api.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.PublicKey;
import java.util.List;

@Api(value = "CRUD rest controller  for Comment resource")
@RestController
@RequestMapping("api/posts")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //create comment for post api
    @ApiOperation(value = "Rest API for creating  a comment")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable Long postId){
        return new ResponseEntity<>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
    }

    //get comments by post id api
    @ApiOperation(value = "Rest API for getting  all comments")
    @GetMapping("/{postId}/getComments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable Long postId){
        return commentService.getAllCommentsByPostId(postId);
    }

    //get comment by post id and comment id
    @ApiOperation(value = "Rest API for getting  a comment by id")
    @GetMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId,
                                                     @PathVariable Long commentId){

        CommentDto responseComment = commentService.getCommentByID(postId, commentId);
        return new ResponseEntity<>(responseComment, HttpStatus.OK);
    }

    //update comment
    @ApiOperation(value = "Rest API for updating  a comment")
    @PutMapping("/{postId}/updateComment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @Valid @RequestBody CommentDto commentDto,
                                                    @PathVariable Long commentId){
        CommentDto updatedComment = commentService.updateComment(postId, commentDto, commentId);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    //delete comment
    @ApiOperation(value = "Rest API for deleting  a comment")
    @DeleteMapping("/{postId}/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    //search comment api
    @GetMapping("/searchComment")
    public List<CommentDto> searchComment(@RequestParam(value = "query") String query){
        return commentService.searchComment(query);
    }
}
