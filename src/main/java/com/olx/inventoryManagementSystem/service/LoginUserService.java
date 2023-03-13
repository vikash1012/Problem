package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.InvalidLoginCredential;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import com.olx.inventoryManagementSystem.utils.LoadByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService{
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    LoadByUsername loadByUsername;

    private JwtUtil jwtUtil;

    @Autowired
    public LoginUserService(UserRepository userRepository, JwtUtil jwtUtil, LoadByUsername loadByUsername) {
        this.userRepository = userRepository;
        this.loadByUsername = loadByUsername;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse createAuthenticationToken(UserRequest userRequest) throws InvalidLoginCredential {
        authenticateUser(userRequest);
        // TODO inline variables
        UserDetails userDetails = loadByUsername.loadUserByUsername(userRequest.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        return new LoginResponse(jwt);
    }

    private void authenticateUser(UserRequest userRequest) throws InvalidLoginCredential {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),
                    userRequest.getPassword()));
        } catch (Exception e) {
            // TODO: proper exception handling
            throw new InvalidLoginCredential("Invalid Login Credential");
        }
    }

}

