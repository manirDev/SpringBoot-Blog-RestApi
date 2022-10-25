package com.springboot.blog.rest.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(value = "Comment resource information")
@Data
public class CommentDto {
    @ApiModelProperty(value = "Blog comment id")
    private Long id;
    @ApiModelProperty(value = "Blog comment name")
    @NotEmpty(message = "Name should not be empty or null")
    private String name;
    @ApiModelProperty(value = "Blog comment email")
    @NotEmpty(message = "Email should not be empty or null")
    @Email
    private String email;
    @ApiModelProperty(value = "Blog comment body")
    @NotEmpty
    @Size(min = 5, message = "Body should be at least 5 characters")
    private String body;
}
