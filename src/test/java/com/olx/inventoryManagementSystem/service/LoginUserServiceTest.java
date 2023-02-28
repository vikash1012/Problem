package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {
    @Mock
    UserRepository userRepository;
    LoginUserService loginUserService;
    @Mock
    JwtUtil jwtUtil=new JwtUtil();
    @Mock
    AuthenticationManager authenticationManager;
    private static UserDetails dummy;
    private static String jwtToken;

    @BeforeEach
    void setup() {
        loginUserService = new LoginUserService(userRepository);
        dummy = new org.springframework.security.core.userdetails.User("user@email.com", "123456", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    public void ShouldReturnLoginResponse() throws Exception{
        User user = new User("parimalvarma@gmail.com","vparimal587");
        UserRequest userRequest = new UserRequest("parimalvarma@gmail.com","vparimal587");
        ResponseEntity<LoginResponse> expectedResponse = new ResponseEntity<>(new LoginResponse("124"), HttpStatus.CREATED);
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(dummy)).thenReturn("124");
        when(userRepository.userExistByEmail("parimalvarma@gmail.com")).thenReturn(user);

        //UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
        ResponseEntity<LoginResponse> actualResponse = loginUserService.createAuthenticationToken(new UserRequest("parimalvarma@gmail.com","vparimal587"));
        //verify(jwtUtil, times(1)).generateToken(userDetails);
        //verify(authenticationManager, times(1)).authenticate(token);
        assertEquals(expectedResponse,actualResponse);
    }


}