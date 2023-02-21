package com.olx.inventoryManagementSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarAttributes {
    String vin;
    String make;
    String model;
    String trim;
    int year;

}
