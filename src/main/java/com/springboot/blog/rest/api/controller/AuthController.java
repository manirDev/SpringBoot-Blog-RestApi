package com.springboot.blog.rest.api.controller;

import com.springboot.blog.rest.api.dto.LoginDto;
import com.springboot.blog.rest.api.dto.SignUpDto;
import com.springboot.blog.rest.api.entity.Role;
import com.springboot.blog.rest.api.entity.User;
import com.springboot.blog.rest.api.repository.RoleRepository;
import com.springboot.blog.rest.api.repository.UserRepository;
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

@RestController
@RequestMapping(path = "api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager  authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/signIn")
    public ResponseEntity<String> authenticator(@RequestBody LoginDto loginDto){
         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUserNameOrEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User is signIn Successfully", HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        //check if email or username exist
        if(userRepository.existsByUserName(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already exists", HttpStatus.BAD_REQUEST.OK);
        }
        if(userRepository.existsByUserName(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already exists", HttpStatus.BAD_REQUEST);
        }

        //create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //add role to the user
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User Successfully registered", HttpStatus.OK);
    }
}
