package com.olx.inventoryManagementSystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Setter
public class ExceptionResponse {
    String errorCode;
    String errorMessage;
}
