package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.UserAlreadyExistsException;
import com.olx.inventoryManagementSystem.exceptions.UserNameNotFoundException;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    WebSecurityConfig webSecurityConfig;

    @Autowired(required = false)
    public UserService(UserRepository userRepository, WebSecurityConfig webSecurityConfig) {
        this.userRepository = userRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    public String createUser(UserRequest userRequest) throws UserAlreadyExistsException {
        if (!userRepository.userExistByEmail(userRequest.getEmail()).isEmpty()) {
            throw new UserAlreadyExistsException("User Already Exists");
        }
        User user = new User(userRequest.getEmail(), (webSecurityConfig.passwordEncoder().encode(userRequest.getPassword())));
        return this.userRepository.createUser(user);
    }
}
