package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.CarAttributes;
import com.olx.inventoryManagementSystem.controller.dto.InventoryRequest;
import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
import com.olx.inventoryManagementSystem.model.Inventory;
import com.olx.inventoryManagementSystem.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public String createInventory(InventoryRequest inventoryRequest)  {
        UUID sku = UUID.randomUUID();

        Inventory inventory = new Inventory(sku.toString(),inventoryRequest.getType(),inventoryRequest.getLocation(),LocalDateTime.now(),LocalDateTime.now(),"user","user", (CarAttributes) inventoryRequest.getAttributes(),inventoryRequest.getCostPrice(),inventoryRequest.getSecondaryStatus());
        return this.inventoryRepository.createInventory(inventory);
    }

    public List<InventoryResponse> getInventories(Pageable pageable) {
        List<InventoryResponse> listOfGetResponses = new ArrayList<>();
        Page<Inventory> listOfInventories = this.inventoryRepository.fetchInventories(pageable);
        for (Inventory inventory : listOfInventories) {
            InventoryResponse inventoryDetails = new InventoryResponse(inventory.getSku(), inventory.getType(), inventory.getStatus(),inventory.getLocation(),inventory.getCreatedAt(),inventory.getUpdatedAt(),inventory.getCreatedBy(),inventory.getUpdatedBy(),inventory.getAttributes(),inventory.getCostPrice(),inventory.getSecondaryStatus());
            listOfGetResponses.add(inventoryDetails);
        }
        return listOfGetResponses;
    }
}
