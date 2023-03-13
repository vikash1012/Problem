package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.LoginResponse;
import com.olx.inventoryManagementSystem.controller.dto.UserRequest;
import com.olx.inventoryManagementSystem.exceptions.InvalidLoginCredential;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.security.WebSecurityConfig;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {

    // TODO : DO NOT USE LAZY

    @Lazy
    @Autowired
    UserRepository userRepository;

    @Lazy
    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Lazy
    @Autowired
    private JwtUtil jwtUtil;

    // TODO: remove multiple constructor for testing and remove required false
    @Autowired(required = false)
    public LoginUserService(@Lazy UserRepository userRepository, @Lazy WebSecurityConfig webSecurityConfig, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.webSecurityConfig = webSecurityConfig;
        this.jwtUtil = jwtUtil;
    }

    public LoginUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.userExistByEmail(username);
        // TODO: user respository should return Optional<User>
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public LoginResponse createAuthenticationToken(UserRequest userRequest) throws InvalidLoginCredential {
        authenticateUser(userRequest);
        // TODO inline variables
        UserDetails userDetails = loadUserByUsername(userRequest.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        return new LoginResponse(jwt);
    }

    private void authenticateUser(UserRequest userRequest) throws InvalidLoginCredential {
        try {
            webSecurityConfig.authenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),
                    userRequest.getPassword()));
        } catch (Exception e) {
            // TODO: proper exception handling
            throw new InvalidLoginCredential("Invalid Login Credential");
        }
    }

}

