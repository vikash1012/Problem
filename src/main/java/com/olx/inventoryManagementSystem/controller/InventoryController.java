package com.olx.inventoryManagementSystem.controller;

//import com.olx.inventoryManagementSystem.controller.dto.CarAttributes;
import com.olx.inventoryManagementSystem.controller.dto.CarAttributes;
import com.olx.inventoryManagementSystem.controller.dto.CreateInventoryResponse;
import com.olx.inventoryManagementSystem.controller.dto.InventoryRequest;
import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
import com.olx.inventoryManagementSystem.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public CreateInventoryResponse createCar(@RequestBody InventoryRequest<CarAttributes> inventoryRequest) {
        String inventoryId = this.inventoryService.createInventory(inventoryRequest);
        return new CreateInventoryResponse(inventoryId);
    }

    @GetMapping("/inventories")
    public List<InventoryResponse> getInventories(@PageableDefault(
            page = 0, size = 10,
            sort = "sku", direction = Sort.Direction.ASC) Pageable pageable) {
        return this.inventoryService.getInventories(pageable);
    }
}
