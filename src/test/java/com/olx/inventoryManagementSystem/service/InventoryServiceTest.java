package com.olx.inventoryManagementSystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.olx.inventoryManagementSystem.controller.dto.InventoryRequest;
import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.olx.inventoryManagementSystem.exceptions.InvalidTypeException;
import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
import com.olx.inventoryManagementSystem.model.Inventory;
import com.olx.inventoryManagementSystem.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    InventoryRepository inventoryRepository;

    InventoryService inventoryService;

    @BeforeEach
    void setup() {
        inventoryService = new InventoryService(inventoryRepository);
    }

//    @Test
//    void ShouldReturnInventorySku() throws Exception{
//        String expectedInventorySku = "d59fdbd5-0c56-4a79-8905-6989601890be";
//        JsonNode attributes = new ObjectMapper().createObjectNode();
//        ((ObjectNode) attributes).put("vin", "AP31CM9873");
//        ((ObjectNode) attributes).put("make", "Tata");
//        ((ObjectNode) attributes).put("model", "Nexon");
//        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
//        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
//        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
//        InventoryRequest inventoryRequest = new InventoryRequest("bike","mumbai",attributes,450000,secondaryStatus);
//        when(inventoryRepository.createInventory(new Inventory("d59fdbd5-0c56-4a79-8905-6989601890be", "bike", "mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus))).thenReturn("d59fdbd5-0c56-4a79-8905-6989601890be");
//
//        String actualInventorySku = inventoryService.createInventory(inventoryRequest);
//
//        assertEquals(expectedInventorySku, actualInventorySku);
//    }

    @Test
    void ShouldThrowInvalidTypeException() throws InvalidTypeException {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        InventoryRequest inventoryRequest = new InventoryRequest("mobile","mumbai",attributes,450000,secondaryStatus);
        String expected="mobile is not supported";

        Exception actualError= Assertions.assertThrows(Exception.class, ()->inventoryService.createInventory(inventoryRequest));
        assertEquals(expected,actualError.getMessage());
    }

    @Test
    void ShouldReturnListOfInventories() {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        List<InventoryResponse> expectedInventories = List.of(new InventoryResponse("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "created", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("sku")));
        Page<Inventory> fetchedInventories = new PageImpl<>(List.of(new Inventory("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus)));
        when(inventoryRepository.fetchInventories(pageable)).thenReturn(fetchedInventories);

        List<InventoryResponse> actualInventories = inventoryService.getInventories(pageable);

        assertEquals(expectedInventories, actualInventories);
    }

    @Test
    void ShouldReturnInventory() throws InventoryNotFoundException {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse", "in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit", "in-progress"));
        InventoryResponse expectedInventory = new InventoryResponse("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "created", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
        Inventory foundInventory = new Inventory("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
        when(inventoryRepository.findInventory("d59fdbd5-0c56-4a79-8905-6989601890be")).thenReturn(foundInventory);

        InventoryResponse actualInventory = inventoryService.getInventory("d59fdbd5-0c56-4a79-8905-6989601890be");

        assertEquals(expectedInventory, actualInventory);
    }

    @Test
    void ShouldReturnSkuAndUpdateStatus() throws Exception{
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        String expectedSku = "09d6afa5-c898-44a1-bddb-d40a4feeee81";
        when(inventoryRepository.findInventory("09d6afa5-c898-44a1-bddb-d40a4feeee81")).thenReturn(new Inventory("09d6afa5-c898-44a1-bddb-d40a4feeee81", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus));
        when(inventoryRepository.updateStatus("09d6afa5-c898-44a1-bddb-d40a4feeee81",new SecondaryStatus("warehouse","completed"))).thenReturn("09d6afa5-c898-44a1-bddb-d40a4feeee81");

        String actualSku = inventoryService.updateStatus("09d6afa5-c898-44a1-bddb-d40a4feeee81",secondaryStatus);

        assertEquals(expectedSku, actualSku);
    }

    @Test
    void ShouldReturnSkuAndAddStatus() throws Exception{
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        ArrayList<SecondaryStatus> secondaryStatuses = new ArrayList<>();
        secondaryStatuses.add(new SecondaryStatus("legal","in-progress"));
        String expectedSku = "09d6afa5-c898-44a1-bddb-d40a4feeee81";
        when(inventoryRepository.findInventory("09d6afa5-c898-44a1-bddb-d40a4feeee81")).thenReturn(new Inventory("09d6afa5-c898-44a1-bddb-d40a4feeee81", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus));
        when(inventoryRepository.addStatus("09d6afa5-c898-44a1-bddb-d40a4feeee81",new SecondaryStatus("legal","in-progress"))).thenReturn("09d6afa5-c898-44a1-bddb-d40a4feeee81");

        String actualSku = inventoryService.updateStatus("09d6afa5-c898-44a1-bddb-d40a4feeee81",secondaryStatuses);

        assertEquals(expectedSku, actualSku);
    }

    @Test
    void ShouldReturnSkuAndUpdateInventory() throws Exception{
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("year", 2016);
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        Map<String, Object> map = new HashMap<>();
        map.put("status","procured");
        map.put("costPrice",460000);
        JsonNode attributesValue = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributesValue).put("color", "red");
        ((ObjectNode) attributesValue).put("year", 2021);
        map.put("attributes",attributesValue);
        String expectedSku = "09d6afa5-c898-44a1-bddb-d40a4feeee81";
        Inventory inventory = new Inventory("09d6afa5-c898-44a1-bddb-d40a4feeee81", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
        when(inventoryRepository.findInventory("09d6afa5-c898-44a1-bddb-d40a4feeee81")).thenReturn(inventory);

        String actualSku = inventoryService.patchInventory("09d6afa5-c898-44a1-bddb-d40a4feeee81",map);

        assertEquals(expectedSku, actualSku);
    }
}