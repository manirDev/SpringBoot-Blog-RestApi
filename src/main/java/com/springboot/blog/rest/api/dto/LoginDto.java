package com.springboot.blog.rest.api.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String userNameOrEmail;
    private String password;
}
