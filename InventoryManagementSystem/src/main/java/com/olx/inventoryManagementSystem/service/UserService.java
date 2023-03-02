package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<RegistrationResponse> createUser(UserRequest userRequest) {
        if (userRepository.userExistByEmail(userRequest.getEmail()) != null) {
            return new ResponseEntity<>(new RegistrationResponse("User already exists"), HttpStatus.CONFLICT);
        }
        User user = new User(userRequest.getEmail(), (webSecurityConfig.passwordEncoder().encode(userRequest.getPassword())));
        String message = userRepository.createUser(user);
        return new ResponseEntity(new RegistrationResponse(message), HttpStatus.CREATED);
    }
}
