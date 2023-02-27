package com.olx.inventoryManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.olx.inventoryManagementSystem.controller.dto.*;
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
import java.util.*;

@Service
public class InventoryService {
    InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public String createInventory(InventoryRequest inventoryRequest) throws InvalidTypeException {
        if(!inventoryRequest.getType().equalsIgnoreCase("car")&&!inventoryRequest.getType().equalsIgnoreCase("bike")){
            throw new InvalidTypeException(inventoryRequest.getType()+" is not supported");
        }
        UUID sku = UUID.randomUUID();
        LocalDateTime localDateTime = LocalDateTime.now();
        Inventory inventory = new Inventory(sku.toString(),inventoryRequest.getType(), inventoryRequest.getLocation(), localDateTime, localDateTime, "user", "user", (Object) inventoryRequest.getAttributes(), inventoryRequest.getCostPrice(), inventoryRequest.getSecondaryStatus());
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
        for (SecondaryStatus statuses : secondaryStatus) {
            if (!inventorySecondaryStatus.contains(statuses)) {
                this.inventoryRepository.addStatus(sku, statuses);
            }
            else if (inventorySecondaryStatus.contains(statuses)) {
                this.inventoryRepository.updateStatus(sku, statuses);
            }
        }
        return sku;
    }

//    public void partialUpdateInventory(JsonPatch patch, String sku) throws InventoryNotFoundException, JsonPatchException, JsonProcessingException {
//        Inventory inventory = inventoryRepository.findInventory(sku);
//        Inventory inventoryPatched = applyPatchToInventory(patch, inventory);
//        inventoryRepository.updateInventory(inventoryPatched,sku);
//    }

//    private Inventory applyPatchToInventory(
//            JsonPatch patch, Inventory inventory) throws JsonPatchException, JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode patched = patch.apply(objectMapper.convertValue(inventory, JsonNode.class));
//        return objectMapper.treeToValue(patched, Inventory.class);
//    }

    public String patchInventory(String sku, Map<String, JsonNode> field) throws InventoryNotFoundException{
        Inventory inventory =  inventoryRepository.findInventory(sku);
        JsonNode jsonnode = new ObjectMapper().createObjectNode();
        field.forEach((key, value) -> {
            Field foundField = ReflectionUtils.findField(Inventory.class, (String) key);
            if (key == "attributes") {
                for(JsonNode object:value){

                }
            }
            foundField.setAccessible(true);
            ReflectionUtils.setField(foundField, inventory, (Object) value);
        });
        inventoryRepository.updateInventory(inventory);
        return sku;
    }
}
