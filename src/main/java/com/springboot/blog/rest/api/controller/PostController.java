package com.springboot.blog.rest.api.controller;

import com.springboot.blog.rest.api.dto.PostDto;
import com.springboot.blog.rest.api.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //add post api
    @PostMapping("/createPost")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(
                postService.createPost(postDto),
                HttpStatus.CREATED
        );
    }

    //get all posts api
    @GetMapping("/allPosts")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    //get post by id api
    @GetMapping("/getPost/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        return  ResponseEntity.ok(postService.getPostById(postId));
    }

    //update post api
    @PutMapping("/update/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){
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
