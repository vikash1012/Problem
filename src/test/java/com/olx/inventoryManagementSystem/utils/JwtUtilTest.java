package com.olx.inventoryManagementSystem.utils;

import com.olx.inventoryManagementSystem.filters.JwtRequestFilter;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    JwtUtil jwtUtil;
    private static UserDetails dummy;
    private static String jwtToken;
    @BeforeEach
    void init() {
        jwtUtil = new JwtUtil();
    }

    UserDetails userDetails = mock(UserDetails.class);
    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        dummy = new org.springframework.security.core.userdetails.User("user@email.com", "vparimal587", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

        @Test
    void ShouldReturnEmailWhenTokenIsGiven() {
        String expectedEmail = "user@email.com";

        String actualEmail = jwtUtil.extractEmail(jwtToken);

        assertEquals(expectedEmail,actualEmail);
    }

    @Test
    void ShouldReturnGeneratedToken() {
        String actualToken = jwtUtil.generateToken(userDetails);

        assertTrue(!actualToken.isEmpty());
    }

    @Test
    void ShouldReturnFalseWhenTokenIsInvalid() {
        Boolean expectedBool  = false;

        Boolean actualBool = jwtUtil.validateToken(jwtToken,userDetails);

        assertEquals(expectedBool,actualBool);
    }
}