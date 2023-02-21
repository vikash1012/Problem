package com.olx.inventoryManagementSystem.controller.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDto extends InventoryRequest {
    CarAttribute attributes;


}
