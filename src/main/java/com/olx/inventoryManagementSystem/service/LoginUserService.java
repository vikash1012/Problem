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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoginUserService implements UserDetailsService {

    @Lazy
    @Autowired
    UserRepository userRepository;
    @Lazy
    @Autowired
    WebSecurityConfig webSecurityConfig;

    @Lazy
    @Autowired
    private JwtUtil jwtUtil;

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
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

    public ResponseEntity<LoginResponse> createAuthenticationToken(UserRequest userRequest)
            throws InvalidLoginCredential {
        authenticateUser(userRequest);
        UserDetails userDetails = loadUserByUsername(userRequest.getEmail());
        String jwt = jwtUtil.generateToken(userDetails);
        return new ResponseEntity<>(new LoginResponse(jwt), HttpStatus.CREATED);
    }

    private void authenticateUser(UserRequest userRequest) throws InvalidLoginCredential {
        try {
            webSecurityConfig.authenticationManager().authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(),
                    userRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidLoginCredential("Invalid Login Credential");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

