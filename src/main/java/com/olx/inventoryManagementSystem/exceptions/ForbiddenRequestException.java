package com.olx.inventoryManagementSystem.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ForbiddenRequestException extends java.lang.Exception{

    public ForbiddenRequestException(String message) {
        super(message);
    }

}
