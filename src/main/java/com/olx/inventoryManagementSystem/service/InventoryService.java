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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class InventoryService {
    InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    private static void isAcceptablenventoryType(InventoryRequest inventoryRequest) throws InvalidTypeException {
        if (!inventoryRequest.getType().equalsIgnoreCase("car") && !inventoryRequest.getType().equalsIgnoreCase("bike")) {
            throw new InvalidTypeException(inventoryRequest.getType() + " is not supported");
        }
    }

    public String createInventory(InventoryRequest inventoryRequest) throws InvalidTypeException {
        isAcceptablenventoryType(inventoryRequest);
        UUID sku = UUID.randomUUID();
        LocalDateTime localDateTime = LocalDateTime.now();
        Inventory inventory = new Inventory(sku.toString(), inventoryRequest.getType(), inventoryRequest.getLocation(), localDateTime, localDateTime, "user", "user", (Object) inventoryRequest.getAttributes(), inventoryRequest.getCostPrice(), inventoryRequest.getSecondaryStatus());
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

    public String updateStatus(String sku, ArrayList<SecondaryStatus> secondaryStatus) throws InventoryNotFoundException {
        Inventory inventory = this.inventoryRepository.findInventory(sku);
        ArrayList<SecondaryStatus> inventorySecondaryStatus = inventory.getSecondaryStatus();
        updateSecondaryStatus(sku, secondaryStatus, inventorySecondaryStatus);
        return sku;
    }

    private void updateSecondaryStatus(String sku, ArrayList<SecondaryStatus> secondaryStatus, ArrayList<SecondaryStatus> inventorySecondaryStatus) {
        for (SecondaryStatus statuses : secondaryStatus) {
            if (!inventorySecondaryStatus.contains(statuses)) {
                this.inventoryRepository.addStatus(sku, statuses);
            } else if (inventorySecondaryStatus.contains(statuses)) {
                this.inventoryRepository.updateStatus(sku, statuses);
            }
        }
    }

    public String patchInventory(String sku, Map<String, Object> field) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findInventory(sku);
        ObjectMapper mapper = new ObjectMapper();
        field.forEach((key, value) -> {
            Field foundField = ReflectionUtils.findField(Inventory.class, (String) key);
            if (key.equals("attributes")) {
                Map<String, Object> valueMap = mapper.convertValue(value, new TypeReference<Map<String, Object>>() {
                });
                Map<String, Object> prevValueMap = mapper.convertValue(inventory.getAttributes(), new TypeReference<Map<String, Object>>() {
                });
                valueMap.forEach((valueKey, valueValue) -> {
                    prevValueMap.put(valueKey, valueValue);
                });
                foundField.setAccessible(true);
                ReflectionUtils.setField(foundField, inventory, (Object) prevValueMap);
            } else if (!key.equals("attributes")) {
                foundField.setAccessible(true);
                ReflectionUtils.setField(foundField, inventory, (Object) value);
            }
        });
        inventoryRepository.updateInventory(inventory);
        return sku;
    }
}
