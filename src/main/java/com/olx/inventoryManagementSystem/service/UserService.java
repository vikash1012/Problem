package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.InventoryRepository;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired(required = false)
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<RegistrationResponse> createUser(UserRequest userRequest) {
        if (userRepository.userExistByEmail(userRequest.getEmail()) != null) {
            return new ResponseEntity<>(new RegistrationResponse("User already exists"), HttpStatus.CONFLICT);
        }
        User user = new User(userRequest.getEmail(), (passwordEncoder.encode(userRequest.getPassword())));
        String message = userRepository.createUser(user);
        return new ResponseEntity(new RegistrationResponse(message), HttpStatus.CREATED);
    }
}
