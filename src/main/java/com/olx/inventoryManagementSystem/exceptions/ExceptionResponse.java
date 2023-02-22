package com.olx.inventoryManagementSystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    String errorCode;
    String errorMessage;
}
