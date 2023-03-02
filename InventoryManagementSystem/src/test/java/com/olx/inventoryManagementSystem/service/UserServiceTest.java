package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    AuthenticationManagerBuilder auth;
    @Mock
    UserRepository userRepository;

    UserService userService;

    @Mock
    WebSecurityConfig webSecurityConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository, webSecurityConfig);
    }

    @Test
    public void ShouldReturnRegistrationResponseWhenUserIsAlreadyPresent() {
        User user = new User("parimalvarma@gmail.com", "vparimal587");
        when(userRepository.userExistByEmail("parimalvarma@gmail.com")).thenReturn(user);
        ResponseEntity<RegistrationResponse> expectedResponse = new ResponseEntity<>(new RegistrationResponse("User already exists"), HttpStatus.CONFLICT);

        ResponseEntity<RegistrationResponse> actualResponse = userService.createUser(new UserRequest("parimalvarma@gmail.com", "vparimal587"));

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void ShouldReturnRegistrationResponseWhenUserIsCreated() {
        PasswordEncoder passwordEncoder = null;
        UserRequest userRequest = new UserRequest("parimalvarma@gmail.com", "vparimal587");
        when(userRepository.userExistByEmail("parimalvarma@gmail.com")).thenReturn(null);
        when(webSecurityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(userRepository.createUser(any())).thenReturn("User Registered Successfully");
        ResponseEntity<RegistrationResponse> expectedResponse = new ResponseEntity(new RegistrationResponse("User Registered Successfully"), HttpStatus.CREATED);

        ResponseEntity<RegistrationResponse> actualResponse = userService.createUser(new UserRequest("parimalvarma@gmail.com", "vparimal587"));

        assertEquals(expectedResponse, actualResponse);
    }
}