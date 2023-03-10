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

    // TODO: do not change code for the testing purpose! do not add autowired false for testing
    // TODO: do not use lazy!
    @Autowired(required = false)
    public JwtRequestFilter(@Lazy UserRepository userRepository, LoginUserService loginUserService, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.loginUserService = loginUserService;
        this.jwtUtil = jwtUtil;
    }

    @Autowired(required = false)
    public JwtRequestFilter(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         // TODO: magic string!!
        final String authorizationHeader = request.getHeader("Authorization");
        if (isRequestPermittedWithNoAuthorizationHeader(request, response, filterChain)) return;
        if (!authorizationHeader.startsWith(BEARER)) {
            resolver.resolveException(request, response, null, new InvalidTokenException("Token is Invalid"));
            return;
        }
        // TODO: inline variables
        String jwt = authorizationHeader.substring(7);
        String email = getEmail(jwt, request, response);
        validateToken(request, email, jwt);
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request, String email, String jwt) {
        // TODO: remove checks which are not required.
        if (email == null) {
            return;
        }

        // TODO: check if securiry context holder is required or not
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        UserDetails userDetails = this.loginUserService.loadUserByUsername(email);
        if (!jwtUtil.validateToken(jwt, userDetails)) {
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String getEmail(String jwt, HttpServletRequest request, HttpServletResponse response) {
        String email = null;
        try {
            email = jwtUtil.extractEmail(jwt);
        } catch (Exception e) {
            // TODO : do not catch EXCEPTION at the top level.
            resolver.resolveException(request, response, null, new InvalidTokenException("Token is Invalid"));
        }
        return email;
    }

    private boolean isRequestPermittedWithNoAuthorizationHeader(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO: DRY
        // TODO: Magic strings
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return false;
        }
        if (!isPermitted(request)) {
            // TODO: Magic strings
            resolver.resolveException(request, response, null, new ForbiddenRequestException("Forbidden Request"));
            return true;
        }
        filterChain.doFilter(request, response);
        return true;
    }

    private boolean isPermitted(HttpServletRequest request) {
        // TODO: Magic strings
        // TODO: Map of urls which are allowed
        return request.getRequestURI().equals("/users/register") || request.getRequestURI().equals("/users/login")
                || request.getRequestURI().contains("swagger-ui") || request.getRequestURI().contains("api-docs");
    }
}


