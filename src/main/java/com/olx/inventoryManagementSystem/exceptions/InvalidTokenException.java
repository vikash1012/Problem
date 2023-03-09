package com.olx.inventoryManagementSystem.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class InvalidTokenException extends java.lang.Exception {

    public InvalidTokenException(String message) { super(message); }

}
