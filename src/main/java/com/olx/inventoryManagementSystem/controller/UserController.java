package com.olx.inventoryManagementSystem.controller;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.InvalidLoginCredential;
import com.olx.inventoryManagementSystem.service.InventoryService;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import com.olx.inventoryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private LoginUserService loginUserService;

    @Autowired(required = false)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired(required = false)
    public UserController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<RegistrationResponse> createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/users/login")
    public ResponseEntity<LoginResponse> createAuthenticationToken(@RequestBody UserRequest userRequest) throws InvalidLoginCredential {
        return this.loginUserService.createAuthenticationToken(userRequest);
    }
}
