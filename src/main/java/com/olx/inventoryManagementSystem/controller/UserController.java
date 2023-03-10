package com.olx.inventoryManagementSystem.controller;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.RegistrationResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.InvalidLoginCredential;
import com.olx.inventoryManagementSystem.exceptions.UserAlreadyExistsException;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import com.olx.inventoryManagementSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // TODO: should only one one constructor and autowired should not be false!
    @Autowired(required = false)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Autowired(required = false)
    public UserController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
    }

    @PostMapping("/users/register")
    public ResponseEntity<RegistrationResponse> createUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
        this.userService.createUser(userRequest);
        // TODO: do not use magic strings and constants
        return new ResponseEntity<>(new RegistrationResponse("User Successfully Registered"), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<LoginResponse> createAuthenticationToken(@RequestBody UserRequest userRequest) throws InvalidLoginCredential {
        return new ResponseEntity<>(this.loginUserService.createAuthenticationToken(userRequest), HttpStatus.CREATED);
    }
}
