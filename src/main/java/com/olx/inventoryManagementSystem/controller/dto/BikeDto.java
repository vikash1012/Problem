package com.olx.inventoryManagementSystem.controller.dto;

import lombok.Data;

@Data
public class BikeDto extends InventoryRequest{
    BikeAttribute attributes;
}
