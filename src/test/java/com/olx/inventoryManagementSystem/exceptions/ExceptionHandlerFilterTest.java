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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
//        loginUserService = new LoginUserService(userRepository,webSecurityConfig,jwtUtil);
//        dummy = new org.springframework.security.core.userdetails.User("user@email.com", "vparimal587", new ArrayList<>());
//        jwtToken = jwtUtil.generateToken(dummy);
    }

    @Test
    void ShouldReturnInvalidTypeExceptionResponse() throws ServletException, IOException {
//        String sku = "a2efc696-9899-4ff0-9569-b4b6c17125f";
//        RuntimeException exception = new RuntimeException("Forbidden Request");
//
//        InventoryNotFoundException exception = new InventoryNotFoundException("Inventory not found for sku - "+sku);
//        ExceptionResponse expectedResponse = new ExceptionResponse("Forbidden", exception.getMessage());
//        ResponseEntity<Object> edxpectedResponse = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        //request.addHeader("Authorization","xyz");

        //Exception actualError= Assertions.assertThrows(Exception.class, ()->excepetionHandlerFilter.doFilterInternal(request,response,filterChain));
        //assertEquals(expected,actualError.getMessage());

        exceptionHandlerFilter.doFilterInternal(request, response, filterChain);
//        Exception actualError= Assertions.assertThrows(RuntimeException.class, ()->excepetionHandlerFilter.doFilterInternal(request,response,filterChain));
//
//        assertEquals(expected,actualError.getMessage());

    }

//    @Test
//    public void whenUserAccessWithWrongCredentials() throws Exception {
//        UserRequest userRequest = new UserRequest("parimalvarma@gmail.com","vparimal587");
//        RegistrationResponse registrationResponse = new RegistrationResponse("User Registered Successfully");
//        when(userService.createUser(userRequest)).thenReturn(new ResponseEntity<>(registrationResponse, HttpStatus.CREATED));
//
//        MockHttpServletRequestBuilder postRequest = post("/users/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"email\":\"parimalvarma@gmail.com\",\"password\":\"vparimal587\"}");
//        this.mockMvc.perform(postRequest)
//                .andExpect(status().isCreated());
//    }
}