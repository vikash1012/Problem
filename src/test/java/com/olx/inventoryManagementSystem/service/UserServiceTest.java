package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.UserAlreadyExistsException;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    UserService userService;

    @Mock
    WebSecurityConfig webSecurityConfig;

    @BeforeEach
    void setup() {
        userService = new UserService(userRepository, webSecurityConfig);
    }

    @Test
    public void ShouldThrowUserAlreadyException() {
        String email = "user@email.com";
        User user = new User(email, "vparimal587");
        when(userRepository.userExistByEmail(email)).thenReturn(user);
        String expectedResponse = "User Already Exists";

        Exception actualError = Assertions.assertThrows(Exception.class, () -> userService.createUser(new UserRequest(email, "vparimal587")));

        assertEquals(expectedResponse, actualError.getMessage());
        verify(userRepository, times(1)).userExistByEmail(email);
    }

    @Test
    public void ShouldReturnEmailWhenUserIsCreated() throws UserAlreadyExistsException {
        String email = "user@email.com";
        when(userRepository.userExistByEmail(email)).thenReturn(null);
        when(webSecurityConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(userRepository.createUser(any(User.class))).thenReturn(email);
        String expectedResponse = email;

        String actualResponse = userService.createUser(new UserRequest(email, "vparimal587"));

        assertEquals(expectedResponse, actualResponse);
        verify(userRepository, times(1)).userExistByEmail(email);
        verify(webSecurityConfig, times(1)).passwordEncoder();
        verify(userRepository, times(1)).createUser(any(User.class));
    }

}
