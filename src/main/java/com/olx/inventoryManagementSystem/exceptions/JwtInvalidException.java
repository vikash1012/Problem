package com.olx.inventoryManagementSystem.exceptions;

public class JwtInvalidException extends Exception{
    public JwtInvalidException(String message) {
        super(message);
    }
}
