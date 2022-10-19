package com.springboot.blog.rest.api.service.Impl;

import com.springboot.blog.rest.api.dto.PostDto;
import com.springboot.blog.rest.api.entity.Post;
import com.springboot.blog.rest.api.exception.ResourceNotFoundException;
import com.springboot.blog.rest.api.repository.PostRepository;
import com.springboot.blog.rest.api.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //convert Dto to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);

        //convert entity to dto;
        PostDto postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        //convert to dto
        List<PostDto> postDtos = posts.stream()
                                        .map(post -> mapToDTO(post))
                                        .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                                  .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        PostDto postDto = mapToDTO(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post post = postRepository.findById(postId)
                                  .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        if (post != null){
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setContent(postDto.getContent());
        }

        Post updatedPost =  postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                                  .orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
        postRepository.delete(post);
    }

    //convert dto to entity
    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    //convert entity to dto
    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }
}
