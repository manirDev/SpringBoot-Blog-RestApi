package com.springboot.blog.rest.api.service.Impl;

import com.springboot.blog.rest.api.dto.CommentDto;
import com.springboot.blog.rest.api.entity.Comment;
import com.springboot.blog.rest.api.entity.Post;
import com.springboot.blog.rest.api.exception.BlogApiException;
import com.springboot.blog.rest.api.exception.ResourceNotFoundException;
import com.springboot.blog.rest.api.repository.CommentRepository;
import com.springboot.blog.rest.api.repository.PostRepository;
import com.springboot.blog.rest.api.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        //convert dto to entity
        Comment comment = mapToEntity(commentDto);
        //find post in DB
        Post post = findPostById(postId);

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        //convert entity to dto
        CommentDto responseComment = mapToDto(newComment);

        return responseComment;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        //convert to dto
        List<CommentDto> commentDtoList = comments.stream()
                                                   .map(comment -> mapToDto(comment))
                                                   .collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public CommentDto getCommentByID(Long postId, Long commentId) {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        CommentDto responseComment = mapToDto(comment);
        return responseComment;
    }

    @Override
    public CommentDto updateComment(Long postId, CommentDto commentDto, Long commentId) {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> searchComment(String query) {

        List<Comment> comments = commentRepository.searchComment(query);
        List<CommentDto> commentDtoList = comments.stream()
                                                  .map(comment -> mapToDto(comment))
                                                  .collect(Collectors.toList());

        return commentDtoList;
    }

    //finding comment by id
    private Comment findCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
        return comment;
    }

    //finding post by id
    private Post findPostById(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        return post;
    }
    //convert dto to entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return  comment;
    }

    //convert entity to dto
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }
}
