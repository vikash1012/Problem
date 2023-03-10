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
    public ResponseEntity<Object> handlerInventoryNotFoundException(InventoryNotFoundException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("NotFound", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidTypeException.class})
    public ResponseEntity<Object> handlerInvalidTypeException(InvalidTypeException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidType", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidLoginCredential.class})
    public ResponseEntity<Object> handlerInvalidLoginCredentialException(InvalidLoginCredential ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidCredential", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserNameNotFoundException.class})
    public ResponseEntity<Object> handlerUserNameNotFoundException(UserNameNotFoundException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidUser", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ResponseEntity<Object> handlerUserAlreadyExistsException(UserAlreadyExistsException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("UserExists", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidTokenException.class})
    public ResponseEntity<Object> handlerInvalidTokenException(InvalidTokenException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("InvalidToken", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ForbiddenRequestException.class})
    public ResponseEntity<Object> handlerForbiddenRequestException(ForbiddenRequestException ex) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse("Forbidden", ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    // TODO: write unknown exception handler
//    @ExceptionHandler({Exception.class})
//    public ResponseEntity<Object> handlerForbiddenRequestException(ForbiddenRequestException ex) {
//        final ExceptionResponse exceptionResponse = new ExceptionResponse("Unknown Error", ex.getMessage());
//        return new ResponseEntity<>(exceptionResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
