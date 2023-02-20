package com.olx.inventoryManagementSystem.controller;

import com.olx.inventoryManagementSystem.controller.dto.CreateInventoryResponse;
import com.olx.inventoryManagementSystem.controller.dto.InventoryRequest;
import com.olx.inventoryManagementSystem.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@RestController
public class InventoryController {

    InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/inventories")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateInventoryResponse createCar(@RequestBody InventoryRequest inventoryRequest) {
        String inventoryId = this.inventoryService.createInventory(inventoryRequest);
        return new CreateInventoryResponse(inventoryId);
    }
}
