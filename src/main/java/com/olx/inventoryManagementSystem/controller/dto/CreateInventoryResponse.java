package com.olx.inventoryManagementSystem.controller.dto;

public class CreateInventoryResponse {
    String sku;

    public CreateInventoryResponse(String sku) {
        this.sku = sku;
    }

    public String getId() {
        return sku;
    }
}
