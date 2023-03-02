package com.olx.inventoryManagementSystem.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.inventoryManagementSystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerFilterTest {

    @Mock
    ExceptionHandlerFilter exceptionHandlerFilter = new ExceptionHandlerFilter();

    @Autowired
    private MockMvc mockMvc;

    @Mock
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    UserService userService;

    @Mock
    FilterChain filterChain = mock(FilterChain.class);

    @BeforeEach
    void setUp() {
        exceptionHandlerFilter = new ExceptionHandlerFilter();
    }

    @Test
    void ShouldReturnInvalidTypeExceptionResponse() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        exceptionHandlerFilter.doFilterInternal(request, response, filterChain);
    }
}