package com.springboot.blog.rest.api.controller;

import com.springboot.blog.rest.api.dto.PostDto;
import com.springboot.blog.rest.api.dto.PostResponse;
import com.springboot.blog.rest.api.service.PostService;
import com.springboot.blog.rest.api.utils.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //add post api
    @PostMapping("/createPost")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(
                postService.createPost(postDto),
                HttpStatus.CREATED
        );
    }

    //get all posts api
    @GetMapping("/allPosts")
    public PostResponse getAllPosts(
            @RequestParam(value ="pageNo", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value ="pageSize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value ="sortBy", defaultValue = Constant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value ="sortDir", defaultValue = Constant.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //get post by id api
    @GetMapping("/getPost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        return  ResponseEntity.ok(postService.getPostById(postId));
    }

    //update post api
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long postId){
        PostDto postResponse = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //delete post api
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("The post with id:  "+postId+"  is deleted successfully", HttpStatus.OK);
    }
}
