package com.olx.springDemo.exceptions;

public class CarNotFoundException extends Exception {

    public CarNotFoundException(String message) {
        super(message);
    }
}
