package com.olx.inventoryManagementSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BikeAttribute {
    String vin;
    String make;
    String model;
    String trim;
    int year;
    int noOfGear;
}