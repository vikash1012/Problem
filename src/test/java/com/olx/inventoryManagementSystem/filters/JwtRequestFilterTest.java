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
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class JwtRequestFilterTest {
    private static String jwtToken;
    private static UserDetails dummy;
    JwtRequestFilter jwtRequestFilter;
    @Mock
    JPAUserRepository jpaUserRepository = mock(JPAUserRepository.class);
    @Mock
    UserRepository userRepository = new UserRepository(jpaUserRepository);
    @Mock
    LoginUserService loginUserService = new LoginUserService(userRepository);
    @Mock
    JwtUtil jwtUtil = new JwtUtil();
    @Mock
    WebSecurityConfig webSecurityConfig;

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
        request.addHeader("Authorization", "Bearer "+jwtToken);
        when(loginUserService.loadUserByUsername("user@email.com")).thenReturn(dummy);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(loginUserService,times(1)).loadUserByUsername("user@email.com");

    }

    @Test
    void ShouldThrowRuntimeExceptionWhenTokenIsInvalid() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.addHeader("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY3NzY4NzY1MSwiaWF0IjoxNjc3NjY5NjUxfQ.2UaKNDmUbgtnfdYI3WzTY4RjcboZJM9LOdGMYQqD95");
        String expectedException = "Token Invalid";

        RuntimeException actualException = Assertions.assertThrows(RuntimeException.class, () -> jwtRequestFilter.doFilterInternal(request, response, filterChain));

        assertEquals(expectedException, actualException.getMessage());
    }

    @Test
    void WhenAuthorizationHeaderIsNullAndIsPermittedForLogin() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.setRequestURI("/users/login");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    void WhenAuthorizationHeaderIsNullAndIsPermittedForRegister() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.setRequestURI("/users/register");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);
    }

    @Test
    void WhenAuthorizationHeaderIsNullAndIsNotPermitted() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        request.setMethod("POST");
        String expectedException = "Forbidden Request";

        RuntimeException actualException = Assertions.assertThrows(RuntimeException.class, () -> jwtRequestFilter.doFilterInternal(request, response, filterChain));

        assertEquals(expectedException, actualException.getMessage());
    }
}
