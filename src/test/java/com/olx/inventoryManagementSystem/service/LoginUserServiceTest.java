package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import com.olx.inventoryManagementSystem.utils.LoadByUsername;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class LoginUserServiceTest {

    private static UserDetails dummy;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtUtil jwtUtil = new JwtUtil();

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    LoadByUsername loadByUsername;

    @InjectMocks
    private LoginUserService loginUserService;

    @BeforeEach
    void setup() {
        loginUserService = new LoginUserService(userRepository, jwtUtil,loadByUsername);
        dummy = new org.springframework.security.core.userdetails.User("user@email.com", "123456", new ArrayList<>());
    }

    @Test
    public void ShouldReturnLoginResponse() throws Exception {
        LoginResponse expectedResponse = new LoginResponse("124");
        UserRequest userRequest = new UserRequest("user@email.com", "123456");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        Authentication authentication = mock(Authentication.class);
        //when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()))).thenReturn(authentication);
        when(jwtUtil.generateToken(dummy)).thenReturn("124");
        when(loadByUsername.loadUserByUsername("user@email.com")).thenReturn(dummy);
        when(userRepository.userExistByEmail("user@email.com")).thenReturn(new User("user@email.com", "123456"));

        LoginResponse actualResponse = loginUserService.createAuthenticationToken(userRequest);

        assertEquals(expectedResponse, actualResponse);
        verify(jwtUtil, times(1)).generateToken(dummy);
        verify(userRepository, times(1)).userExistByEmail("user@email.com");
    }

    @Test
    public void ShouldThrowUsernameNotFoundException() throws Exception {
        when(userRepository.userExistByEmail("user@email.com")).thenReturn(null);
        String expected = "user@email.com not found.";

        Exception actualError = Assertions.assertThrows(Exception.class, () -> loadByUsername.loadUserByUsername("user@email.com"));

        assertEquals(expected, actualError.getMessage());
        verify(userRepository, times(1)).userExistByEmail("user@email.com");
    }

    @Test
    public void ShouldThrowInvalidLoginCredentialException() throws Exception {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = mock(UsernamePasswordAuthenticationToken.class);
        String expectedResponse = "Invalid Login Credential";

        Exception actualError = Assertions.assertThrows(Exception.class, () -> loginUserService.createAuthenticationToken(new UserRequest("user@email.com", "124")));

        assertEquals(expectedResponse, actualError.getMessage());
    }

}