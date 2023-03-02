package com.olx.inventoryManagementSystem.controller;

import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean({UserService.class})
@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
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
        UserController userController = new UserController(userService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void ShouldReturnRegistrationResponse() throws Exception {
        UserRequest userRequest = new UserRequest("parimalvarma@gmail.com", "vparimal587");
        RegistrationResponse registrationResponse = new RegistrationResponse("User Registered Successfully");
        when(userService.createUser(userRequest)).thenReturn(new ResponseEntity<>(registrationResponse, HttpStatus.CREATED));

        MockHttpServletRequestBuilder postRequest = post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"parimalvarma@gmail.com\",\"password\":\"vparimal587\"}");
        this.mockMvc.perform(postRequest)
                .andExpect(status().isCreated());
    }

}