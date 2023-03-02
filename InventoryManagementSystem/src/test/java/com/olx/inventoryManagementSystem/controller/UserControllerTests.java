package com.olx.inventoryManagementSystem.controller;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.repository.JPAUserRepository;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import com.olx.inventoryManagementSystem.service.UserService;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean({UserService.class})
@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTests {
    @MockBean
    UserService userService;
    @MockBean
    LoginUserService loginUserService;
    @MockBean
    JwtUtil jwtUtilBean;
    @MockBean
    UserRepository userRepositoryBean;
    @MockBean
    JPAUserRepository jpaUserRepositoryBean;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        UserController userController = new UserController(loginUserService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    @BeforeTestMethod
    void ShouldReturnLoginResponse() throws Exception {
        UserRequest userRequest = new UserRequest("parimalvarma@gmail.com", "vparimal587");
        LoginResponse loginResponse = new LoginResponse("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aW1hbGphaW5AZ21haWwuY29tIiwiZXhwIjoxNjc3NTkzMzk1LCJpYXQiOjE2Nzc1NzUzOTV9.olt1Ma8eYtoN-efVLs7R2G5wExb_xDNa9UeLQqph1Pg");
        when(loginUserService.createAuthenticationToken(userRequest)).thenReturn(new ResponseEntity<>(loginResponse, HttpStatus.CREATED));

        MockHttpServletRequestBuilder postRequest = post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"parimalvarma@gmail.com\",\"password\":\"vparimal587\"}");
        this.mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

}