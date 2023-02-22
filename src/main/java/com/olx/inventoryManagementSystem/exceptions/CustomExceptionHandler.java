package com.olx.inventoryManagementSystem.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@NoArgsConstructor
public class CustomExceptionHandler {
    @ExceptionHandler({InventoryNotFoundException.class})
    public ResponseEntity<Object> handlerCarNotFoundException(InventoryNotFoundException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("NotFound", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);


    }
    @ExceptionHandler({InvalidTypeException.class})
    public ResponseEntity<Object> handlerInvalidTypeException(InvalidTypeException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidType", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
