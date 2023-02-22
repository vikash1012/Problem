//package com.olx.inventoryManagementSystem.service;
//
//import com.olx.inventoryManagementSystem.controller.dto.CarDto;
//import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
//import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
//import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
//import com.olx.inventoryManagementSystem.model.Inventory;
//import com.olx.inventoryManagementSystem.repository.InventoryRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class InventoryServiceTest {
//
//    @Mock
//    InventoryRepository inventoryRepository;
//
//    InventoryService inventoryService;
//
//    @BeforeEach
//    void setup() {
//        inventoryService = new InventoryService(inventoryRepository);
//    }
//
//    @Test
//    void ShouldReturnListOfInventories() {
//        CarDto attributes = new CarDto("AP42BM1234","Tata", "Nexon", "EX", 2021);
//        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
//        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("sku")));;
//        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
//        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
//        List<InventoryResponse> expectedInventories = List.of(new InventoryResponse("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "created", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus));
//        Page<Inventory> fetchedInventories = new PageImpl<>(List.of(new Inventory("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus)));
//        when(inventoryRepository.fetchInventories(pageable)).thenReturn(fetchedInventories);
//
//        List<InventoryResponse> actualInventories = inventoryService.getInventories(pageable);
//
//        assertEquals(expectedInventories, actualInventories);
//    }
//
//    @Test
//    void ShouldReturnInventory() throws InventoryNotFoundException {
//        CarDto attributes = new CarDto("AP42BM1234","Tata", "Nexon", "EX", 2021);
//        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
//        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
//        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
//        InventoryResponse expectedInventory = new InventoryResponse("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "created", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
//        Inventory foundInventory = new Inventory("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus);
//        when(inventoryRepository.findInventory("d59fdbd5-0c56-4a79-8905-6989601890be")).thenReturn(foundInventory);
//
//        InventoryResponse actualInventory = inventoryService.getInventory("d59fdbd5-0c56-4a79-8905-6989601890be");
//
//        assertEquals(expectedInventory, actualInventory);
//    }
//}