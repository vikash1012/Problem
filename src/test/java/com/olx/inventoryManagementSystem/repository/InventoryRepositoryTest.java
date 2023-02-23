package com.olx.inventoryManagementSystem.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
import com.olx.inventoryManagementSystem.model.Inventory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryRepositoryTest {
    InventoryRepository inventoryRepository;
    @Mock
    JPAInventoryRepository jpaInventoryRepository;

    @BeforeEach
    void init() {
        inventoryRepository = new InventoryRepository(jpaInventoryRepository);
    }

    @Test
    void shouldReturnIdFromDB() {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse", "in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit", "in-progress"));
        Inventory inventory = new Inventory("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
        String expectedId = "d59fdbd5-0c56-4a79-8905-6989601890be";
        when(jpaInventoryRepository.save(inventory)).thenReturn(inventory);

        String actualId = inventoryRepository.createInventory(inventory);

        assertEquals(expectedId,actualId);


    }

    @Test
    void shouldReturnnventoryForValidSku() throws InventoryNotFoundException {
        String sku = "d59fdbd5-0c56-4a79-8905-6989601890be";
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse", "in-repair"));
        Inventory inventory = new Inventory("d59fdbd5-0c56-4a79-8905-6989601890bf", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
        Inventory expected=inventory;
        when(jpaInventoryRepository.findById(sku)).thenReturn(Optional.of(inventory));

        Inventory actual=inventoryRepository.findInventory(sku);

        assertEquals(expected,actual);



    }

    @Test
    void shouldReturnInventoryForInValidSku() throws InventoryNotFoundException {
        String sku = "d59fdbd5-0c56-4a79-8905-6989601890be";
        Optional<Inventory> inventory=Optional.empty();
        when(jpaInventoryRepository.findById(sku)).thenReturn(inventory);
        String expectedError="Inventory not found for sku - "+sku;

        InventoryNotFoundException actualError= Assertions.assertThrows(InventoryNotFoundException.class,()->inventoryRepository.findInventory(sku));

        assertEquals(expectedError,actualError.getMessage());




    }

    @Test
    void shouldReturnfetchInventories() {
    }

    @Test
    void updateStatus() {
    }

    @Test
    void addStatus() {
    }
}