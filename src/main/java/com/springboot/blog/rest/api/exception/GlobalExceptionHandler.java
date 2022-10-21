package com.springboot.blog.rest.api.exception;

import com.springboot.blog.rest.api.response.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler{

    //specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    //specific exception
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetail> handleBlogApiException(BlogApiException exception, WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    //global exception

    @ExceptionHandler(Exception .class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception exception, WebRequest webRequest){
        ErrorDetail errorDetail = new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
