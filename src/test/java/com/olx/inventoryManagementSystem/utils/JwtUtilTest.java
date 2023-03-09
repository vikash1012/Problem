package com.olx.inventoryManagementSystem.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class JwtUtilTest {

    private static UserDetails dummy;

    private static String jwtToken;

    JwtUtil jwtUtil;

    UserDetails userDetails = mock(UserDetails.class);

    @BeforeEach
    void init() {
        jwtUtil = new JwtUtil();
    }

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

        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    void ShouldReturnGeneratedToken() {
        String actualToken = jwtUtil.generateToken(userDetails);

        assertTrue(!actualToken.isEmpty());
    }

    @Test
    void ShouldReturnFalseWhenTokenIsInvalid() {
        Boolean expectedBool = false;

        Boolean actualBool = jwtUtil.validateToken(jwtToken, userDetails);

        assertEquals(expectedBool, actualBool);
    }
}