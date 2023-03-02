package com.olx.springDemo.controller.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CarRequest {
    String make;
    String model;
    String color;

    public CarRequest(String make, String model, String color) {
        this.make = make;
        this.model = model;
        this.color = color;
    }
    public CarRequest(){

    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

// dto - Data Transfer Object
