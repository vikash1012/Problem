package com.olx.inventoryManagementSystem.filters;

import com.olx.inventoryManagementSystem.repository.JPAUserRepository;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class JwtRequestFilterTest {
    JwtRequestFilter jwtRequestFilter;
    @Mock
    JPAUserRepository jpaUserRepository = mock(JPAUserRepository.class);
    @Mock
    UserRepository userRepository = new UserRepository(jpaUserRepository);
    @Mock
    JwtUtil jwtUtil = new JwtUtil();
    @Mock
    WebSecurityConfig webSecurityConfig;
    @Mock
    LoginUserService loginUserService = new LoginUserService(userRepository);

    private static String jwtToken;

    private static UserDetails dummy;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        jwtRequestFilter = new JwtRequestFilter(userRepository, loginUserService, jwtUtil);
        dummy = new org.springframework.security.core.userdetails.User("user@email.com", "vparimal587", new ArrayList<>());
        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    void WhenAuthorizationHeaderIsPresent() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY3NzY4NzY1MSwiaWF0IjoxNjc3NjY5NjUxfQ.2UaKNDmUbgtnfdYI3WzTY4RjcboZJM9LOdGMYQqD95A");
        when(loginUserService.loadUserByUsername("user@email.com")).thenReturn(dummy);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    void ShouldThrowRuntimeExceptionWhenTokenIsInvalid() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY3NzY4NzY1MSwiaWF0IjoxNjc3NjY5NjUxfQ.2UaKNDmUbgtnfdYI3WzTY4RjcboZJM9LOdGMYQqD95");
        String expectedException = "Token Invalid";

        RuntimeException actualException = Assertions.assertThrows(RuntimeException.class, () -> jwtRequestFilter.doFilterInternal(request, response, filterChain));

        assertEquals(expectedException, actualException.getMessage());
    }

    @Test
    void WhenAuthorizationHeaderIsNullAndIsPermitted() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.setMethod("GET");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    void WhenAuthorizationHeaderIsNullAndIsNotPermitted() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.setMethod("POST");
        String expectedException = "Forbidden Request";

        RuntimeException actualException = Assertions.assertThrows(RuntimeException.class, () -> jwtRequestFilter.doFilterInternal(request, response, filterChain));

        assertEquals(expectedException, actualException.getMessage());
    }
}