package com.springboot.blog.rest.api.exception;

import com.springboot.blog.rest.api.response.ErrorDetail;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    //bean validation exception
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((objectError -> {
            String fieldName = ((FieldError)objectError).getField();
            String message  = objectError.getDefaultMessage();
            errors.put(fieldName, message);
        }));

        return  new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
