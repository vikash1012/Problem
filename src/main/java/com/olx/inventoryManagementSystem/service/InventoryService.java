package com.olx.inventoryManagementSystem.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.inventoryManagementSystem.controller.dto.InventoryRequest;
import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.olx.inventoryManagementSystem.exceptions.InvalidTypeException;
import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
import com.olx.inventoryManagementSystem.model.Inventory;
import com.olx.inventoryManagementSystem.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class InventoryService {
    InventoryRepository inventoryRepository;

    private final static String CAR_TYPE = "car";

    private final static String BIKE_TYPE = "bike";

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public String createInventory(InventoryRequest inventoryRequest) throws InvalidTypeException {
        isAcceptableInventoryType(inventoryRequest.getType());
        Inventory inventory = new Inventory(inventoryRequest.getType(), inventoryRequest.getLocation(), "user",
                "user", (Object) inventoryRequest.getAttributes(), inventoryRequest.getCostPrice(),
                inventoryRequest.getSecondaryStatus());
        return this.inventoryRepository.createInventory(inventory);
    }

    public InventoryResponse getInventory(String InventorySku) throws InventoryNotFoundException {
        Inventory inventory = this.inventoryRepository.findInventory(InventorySku);
        return new InventoryResponse(inventory.getSku(), inventory.getType(), inventory.getStatus(),
                inventory.getLocation(), inventory.getCreatedAt(), inventory.getUpdatedAt(), inventory.getCreatedBy(),
                inventory.getUpdatedBy(), inventory.getAttributes(), inventory.getCostPrice(),
                inventory.getSecondaryStatus());
    }

    public List<InventoryResponse> getInventories(Pageable pageable) {
        List<InventoryResponse> listOfGetResponses = new ArrayList<>();
        Page<Inventory> listOfInventories = this.inventoryRepository.fetchInventories(pageable);
        for (Inventory inventory : listOfInventories) {
            InventoryResponse inventoryDetails = new InventoryResponse(inventory.getSku(), inventory.getType(),
                    inventory.getStatus(), inventory.getLocation(), inventory.getCreatedAt(), inventory.getUpdatedAt(),
                    inventory.getCreatedBy(), inventory.getUpdatedBy(), inventory.getAttributes(),
                    inventory.getCostPrice(), inventory.getSecondaryStatus());
            listOfGetResponses.add(inventoryDetails);
        }
        return listOfGetResponses;
    }

    public void updateStatus(String sku, ArrayList<SecondaryStatus> secondaryStatus)
            throws InventoryNotFoundException {
        Inventory inventory = this.inventoryRepository.findInventory(sku);
        inventory.UpdateStatus(inventory, secondaryStatus);
        inventoryRepository.saveInventory(inventory);
    }

    public void patchInventory(String sku, Map<String, Object> field) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findInventory(sku);
        updateInventory(field, inventory);
        inventoryRepository.saveInventory(inventory);
    }

    private static void isAcceptableInventoryType(String type) throws InvalidTypeException {
        if (!type.equalsIgnoreCase(CAR_TYPE)
                && !type.equalsIgnoreCase(BIKE_TYPE)) {
            throw new InvalidTypeException(type + " is not supported");
        }
    }

    private static void updateInventory(Map<String, Object> field, Inventory inventory) {
        field.forEach((key, value) -> {
            Field foundField = ReflectionUtils.findField(Inventory.class, key);
            if (!key.equals("attributes")) {
                setFields(inventory, value, foundField);
                return;
            }
            updateAttributes(inventory, value, foundField);
            setFields(inventory, value, foundField);
        });
    }

    private static void setFields(Inventory inventory, Object value, Field foundField) {
        foundField.setAccessible(true);
        ReflectionUtils.setField(foundField, inventory, value);
    }

    private static void updateAttributes(Inventory inventory, Object value, Field foundField) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> valueMap = mapper.convertValue(value, new TypeReference<Map<String, Object>>() {
        });
        Map<String, Object> prevValueMap = mapper.convertValue(inventory.getAttributes(),
                new TypeReference<Map<String, Object>>() {
                });
        prevValueMap.putAll(valueMap);
        foundField.setAccessible(true);
        ReflectionUtils.setField(foundField, inventory, prevValueMap);
    }

}
