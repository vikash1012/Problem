package com.olx.inventoryManagementSystem.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.inventoryManagementSystem.exceptions.CustomExceptionHandler;
import com.olx.inventoryManagementSystem.exceptions.ForbiddenRequestException;
import com.olx.inventoryManagementSystem.exceptions.InvalidLoginCredential;
import com.olx.inventoryManagementSystem.exceptions.InvalidTokenException;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private CustomExceptionHandler exceptionHandler;

    @Autowired(required = false)
    public JwtRequestFilter(@Lazy UserRepository userRepository, LoginUserService loginUserService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.loginUserService = loginUserService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;
        if (isRequestPermittedWithNoAuthorizationHeader(request, response, filterChain)) return;
        jwt = authorizationHeader.substring(7);
        email = getEmail(authorizationHeader, jwt);
        validateToken(request, email, jwt);
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request, String email, String jwt) {
        if (email == null) {
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        UserDetails userDetails = this.loginUserService.loadUserByUsername(email);
        if (!jwtUtil.validateToken(jwt, userDetails)) {
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String getEmail(String authorizationHeader, String jwt){
        String email;
        try {
            email = jwtUtil.extractEmail(jwt);
        } catch (Exception e) {
            // TODO : Do not throw run time exception
            throw new RuntimeException("Token is Invalid");
        }
        return email;
    }

    private boolean isRequestPermittedWithNoAuthorizationHeader(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return false;
        }
        if (!isPermitted(request)) {
            throw new RuntimeException("Forbidden Request");
        }
        filterChain.doFilter(request, response);
        return true;
    }

    private boolean isPermitted(HttpServletRequest request) {
        return request.getRequestURI().equals("/users/register") || request.getRequestURI().equals("/users/login");
    }
}


