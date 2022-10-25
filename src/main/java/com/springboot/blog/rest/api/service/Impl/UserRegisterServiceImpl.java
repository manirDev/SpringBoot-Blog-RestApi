package com.springboot.blog.rest.api.service.Impl;

import com.springboot.blog.rest.api.dto.SignUpDto;
import com.springboot.blog.rest.api.entity.Role;
import com.springboot.blog.rest.api.entity.User;
import com.springboot.blog.rest.api.repository.RoleRepository;
import com.springboot.blog.rest.api.repository.UserRepository;
import com.springboot.blog.rest.api.service.UserRegisterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public SignUpDto registerUser(SignUpDto signUpDto) {
        User user = mapToEntity(signUpDto);
        User newUser = userRepository.save(user);
        return mapToDto(newUser);
    }

    private User mapToEntity(SignUpDto signUpDto){
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUserName(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //add role to the user
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        return user;
    }

    private SignUpDto mapToDto(User user){
        SignUpDto signUpDto = modelMapper.map(user, SignUpDto.class);
        return signUpDto;
    }
}
