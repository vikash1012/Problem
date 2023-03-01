package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.UserNameNotFoundException;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class LoginUserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    JwtUtil jwtUtil=new JwtUtil();
    @Mock
    WebSecurityConfig webSecurityConfig;
    @InjectMocks
    private LoginUserService loginUserService;
    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    void setup() {
        loginUserService = new LoginUserService(userRepository,webSecurityConfig,jwtUtil);
        dummy = new org.springframework.security.core.userdetails.User("user@email.com", "123456", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    public void ShouldReturnLoginResponse() throws Exception{
        User user = new User("user@email.com","vparimal587");
        ResponseEntity<LoginResponse> expectedResponse = new ResponseEntity<>(new LoginResponse("124"), HttpStatus.CREATED);
        when(webSecurityConfig.authenticationManager()).thenReturn(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }
        });
        when(jwtUtil.generateToken(dummy)).thenReturn("124");
        when(userRepository.userExistByEmail("user@email.com")).thenReturn(user);
        ResponseEntity<LoginResponse> actualResponse = loginUserService.createAuthenticationToken(new UserRequest("user@email.com","vparimal587"));

        assertEquals(expectedResponse,actualResponse);
    }

    @Test
    public void ShouldThrowUsernameNotFoundException() throws Exception{
        when(userRepository.userExistByEmail("user@email.com")).thenReturn(null);
        String expected="user@email.com not found.";

        Exception actualError= Assertions.assertThrows(Exception.class, ()->loginUserService.loadUserByUsername("user@email.com"));

        assertEquals(expected,actualError.getMessage());
    }

    @Test
    public void ShouldThrowInvalidLoginCredentialException() throws Exception{
        User user = new User("user@email.com","vparimal587");
        ResponseEntity<LoginResponse> expectedResponse = new ResponseEntity<>(new LoginResponse("124"), HttpStatus.CREATED);
        when(webSecurityConfig.authenticationManager()).thenThrow(new BadCredentialsException("Invalid Login Credential"));
        String expected="Invalid Login Credential";

        Exception actualError= Assertions.assertThrows(Exception.class, ()->loginUserService.createAuthenticationToken(new UserRequest("user@email.com","124")));

        assertEquals(expected,actualError.getMessage());
    }

    @Test
    public void ShouldThrowsRuntimeException() throws Exception{
        User user = new User("user@email.com","vparimal587");
        ResponseEntity<LoginResponse> expectedResponse = new ResponseEntity<>(new LoginResponse("124"), HttpStatus.CREATED);
        when(webSecurityConfig.authenticationManager()).thenThrow(new Exception("Runtime Exception"));
        String expected="java.lang.Exception: Runtime Exception";

        Exception actualError= Assertions.assertThrows(Exception.class, ()->loginUserService.createAuthenticationToken(new UserRequest("user@email.com","124")));

        assertEquals(expected,actualError.getMessage());
    }

}