package com.olx.inventoryManagementSystem.service;

import com.olx.inventoryManagementSystem.controller.dto.*;
import com.olx.inventoryManagementSystem.exceptions.InvalidTypeException;
import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
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

    public String createInventory(InventoryRequest inventoryRequest) throws InvalidTypeException {
        if (!(inventoryRequest instanceof CarDto) && !(inventoryRequest instanceof BikeDto)) {
            throw new InvalidTypeException("Invalid Inventory Type");
        }
        Object attributes = new Object();
        if (inventoryRequest instanceof CarDto) {
            CarDto carDto = (CarDto) inventoryRequest;
            attributes = carDto.getAttributes();
        } else if (inventoryRequest instanceof BikeDto) {
            BikeDto bikeDto = (BikeDto) inventoryRequest;
            attributes = bikeDto.getAttributes();
        }
        UUID sku = UUID.randomUUID();
        Inventory inventory = new Inventory(sku.toString(), inventoryRequest.getType(), inventoryRequest.getLocation(), LocalDateTime.now(), LocalDateTime.now(), "user", "user", attributes, inventoryRequest.getCostPrice(), inventoryRequest.getSecondaryStatus());
        return this.inventoryRepository.createInventory(inventory);
    }

    public InventoryResponse getInventory(String InventorySku) throws InventoryNotFoundException {
        Inventory inventory = this.inventoryRepository.findInventory(InventorySku);
        InventoryResponse inventoryResponse = new InventoryResponse(inventory.getSku(), inventory.getType(), inventory.getStatus(), inventory.getLocation(), inventory.getCreatedAt(), inventory.getUpdatedAt(), inventory.getCreatedBy(), inventory.getUpdatedBy(), inventory.getAttributes(), inventory.getCostPrice(), inventory.getSecondaryStatus());
        return inventoryResponse;
    }

    public List<InventoryResponse> getInventories(Pageable pageable) {
        List<InventoryResponse> listOfGetResponses = new ArrayList<>();
        Page<Inventory> listOfInventories = this.inventoryRepository.fetchInventories(pageable);
        for (Inventory inventory : listOfInventories) {
            InventoryResponse inventoryDetails = new InventoryResponse(inventory.getSku(), inventory.getType(), inventory.getStatus(), inventory.getLocation(), inventory.getCreatedAt(), inventory.getUpdatedAt(), inventory.getCreatedBy(), inventory.getUpdatedBy(), inventory.getAttributes(), inventory.getCostPrice(), inventory.getSecondaryStatus());
            listOfGetResponses.add(inventoryDetails);
        }
        return listOfGetResponses;
    }

    public void updateStatus(String sku, ArrayList<SecondaryStatus> secondaryStatus) throws InventoryNotFoundException {
        Inventory inventory = this.inventoryRepository.findInventory(sku);
        ArrayList<SecondaryStatus> inventorysecondaryStatus = inventory.getSecondaryStatus();
        for (SecondaryStatus statuses : secondaryStatus) {
            if (!inventorysecondaryStatus.contains(statuses)) {
                this.inventoryRepository.addStatus(sku, statuses);
            } else if (inventorysecondaryStatus.contains(statuses)) {
                this.inventoryRepository.updateStatus(sku, statuses);
            }


        }


    }
}
