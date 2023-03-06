package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
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

    // TODO Service layer doesn't return ResponseEntity Objects :Done
    public RegistrationResponse createUser(UserRequest userRequest) {
        if (userRepository.userExistByEmail(userRequest.getEmail()) != null) {
            return new RegistrationResponse("User already exists");
        }
        User user = new User(userRequest.getEmail(), (webSecurityConfig.passwordEncoder().encode(userRequest.getPassword())));
        this.userRepository.createUser(user);
        return new RegistrationResponse("User Registered Successfully");
    }
}
