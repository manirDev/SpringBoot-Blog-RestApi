package com.springboot.blog.rest.api.service;

import com.springboot.blog.rest.api.dto.SignUpDto;

public interface UserRegisterService {
    SignUpDto registerUser(SignUpDto signUpDto);
}
