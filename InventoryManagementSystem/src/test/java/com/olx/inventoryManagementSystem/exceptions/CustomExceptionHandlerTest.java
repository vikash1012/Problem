package com.olx.inventoryManagementSystem.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {
    CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    void ShouldReturnInvalidTypeExceptionResponse() {
        String sku = "a2efc696-9899-4ff0-9569-b4b6c17125f";
        InventoryNotFoundException exception = new InventoryNotFoundException("Inventory not found for sku - " + sku);
        ExceptionResponse exceptionResponse = new ExceptionResponse("NotFound", exception.getMessage());
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);

        ResponseEntity<Object> actualResponse = customExceptionHandler.handlerInventoryNotFoundException(exception);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void ShouldReturnInventoryNotFoundResponse() {
        String type = "mobile";
        InvalidTypeException exception = new InvalidTypeException(type + " is not supported");
        ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidType", exception.getMessage());
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

        ResponseEntity<Object> actualResponse = customExceptionHandler.handlerInvalidTypeException(exception);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void ShouldReturnInvalidLoginCredentialResponse() {
        InvalidLoginCredential exception = new InvalidLoginCredential("Credentials are Invalid");
        ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidCredential", exception.getMessage());
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);

        ResponseEntity<Object> actualResponse = customExceptionHandler.handlerInvalidLoginCredentialException(exception);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void ShouldReturnUserNameNotFoundResponse() {
        UserNameNotFoundException exception = new UserNameNotFoundException("Username is not found");
        ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidUser", exception.getMessage());
        ResponseEntity<Object> expectedResponse = new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);

        ResponseEntity<Object> actualResponse = customExceptionHandler.handlerUserNameNotFoundException(exception);

        assertEquals(expectedResponse, actualResponse);
    }
}