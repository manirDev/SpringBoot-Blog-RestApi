package com.springboot.blog.rest.api.controller;

import com.springboot.blog.rest.api.dto.JwtAuthResponse;
import com.springboot.blog.rest.api.dto.LoginDto;
import com.springboot.blog.rest.api.dto.SignUpDto;
import com.springboot.blog.rest.api.entity.Role;
import com.springboot.blog.rest.api.entity.User;
import com.springboot.blog.rest.api.repository.RoleRepository;
import com.springboot.blog.rest.api.repository.UserRepository;
import com.springboot.blog.rest.api.security.JwtTokenProvider;
import com.springboot.blog.rest.api.service.UserRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Api(value = "Auth Controller provides signIn and signUp Rest APIs")
@RestController
@RequestMapping(path = "api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager  authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "Rest API for User register to the Blog app")
    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthResponse> authenticator(@RequestBody LoginDto loginDto){
         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from token provider
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @ApiOperation(value = "Rest API for User login to the Blog app")
    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        //check if email or username exist
        if(userRepository.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already exists", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByUserName(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already exists", HttpStatus.BAD_REQUEST);
        }

        //create user object and register
        userRegisterService.registerUser(signUpDto);

        return new ResponseEntity<>("User Successfully registered", HttpStatus.OK);
    }
}
