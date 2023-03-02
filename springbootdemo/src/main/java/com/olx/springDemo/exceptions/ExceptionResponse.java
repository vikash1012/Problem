package com.olx.springDemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    String code;
    String message;
}
