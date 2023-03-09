package com.olx.inventoryManagementSystem.filters;

import com.olx.inventoryManagementSystem.exceptions.ForbiddenRequestException;
import com.olx.inventoryManagementSystem.exceptions.InvalidTokenException;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import com.olx.inventoryManagementSystem.service.LoginUserService;
import com.olx.inventoryManagementSystem.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final static String BEARER = "Bearer ";
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Autowired(required = false)
    public JwtRequestFilter(@Lazy UserRepository userRepository, LoginUserService loginUserService, JwtUtil jwtUtil,HandlerExceptionResolver resolver) {
        this.userRepository = userRepository;
        this.loginUserService = loginUserService;
        this.jwtUtil = jwtUtil;
        this.resolver=resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        if (isRequestPermittedWithNoAuthorizationHeader(request, response, filterChain)) return;
        if (!authorizationHeader.startsWith(BEARER)) {
            resolver.resolveException(request,response,null,new InvalidTokenException("Token is Invalid"));
            return;
        }
        String jwt = authorizationHeader.substring(7);
        String email = getEmail(jwt,request,response);
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

    private String getEmail(String jwt,HttpServletRequest request,HttpServletResponse response) {
        String email = null;
        try {
            email = jwtUtil.extractEmail(jwt);
        } catch (Exception e) {
            resolver.resolveException(request,response,null,new InvalidTokenException("Token is Invalid"));

        }
        return email;
    }

    private boolean isRequestPermittedWithNoAuthorizationHeader(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return false;
        }
        if (!isPermitted(request)) {
            resolver.resolveException(request,response,null,new ForbiddenRequestException("Forbidden Request"));
            return true;
        }
        filterChain.doFilter(request, response);
        return true;
    }

    private boolean isPermitted(HttpServletRequest request) {
        return request.getRequestURI().equals("/users/register") || request.getRequestURI().equals("/users/login");
    }
}


