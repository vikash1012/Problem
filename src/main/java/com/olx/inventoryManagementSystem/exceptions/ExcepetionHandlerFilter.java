package com.olx.inventoryManagementSystem.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExcepetionHandlerFilter extends OncePerRequestFilter {
    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }  catch (RuntimeException e) {
            ExceptionResponse errorResponse=new ExceptionResponse();
                if(e.getMessage().equals("Forbidden Request")){
                     errorResponse = new ExceptionResponse("Forbidden", e.getMessage());

                }
                else if(e.getMessage().equals("Token Invalid")) {
                    errorResponse = new ExceptionResponse("Token InValid", e.getMessage());
                    }
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

        }

        }
    }
