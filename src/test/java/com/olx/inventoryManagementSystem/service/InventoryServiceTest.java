package com.olx.inventoryManagementSystem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.olx.inventoryManagementSystem.controller.dto.InventoryRequest;
import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.olx.inventoryManagementSystem.exceptions.InventoryNotFoundException;
import com.olx.inventoryManagementSystem.model.Inventory;
import com.olx.inventoryManagementSystem.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    InventoryRepository inventoryRepository;


    InventoryService inventoryService;
    @Captor
    ArgumentCaptor<Inventory> inventoryCaptor;


    @BeforeEach
    void setup() {
        inventoryService = new InventoryService(inventoryRepository);

    }

    @Test
    void ShouldReturnInventorySku() throws Exception {
        String expectedSku = "09d6afa5-c898-44a1-bddb-d40a4feeee81";
        InventoryRequest inventoryRequest = new InventoryRequest("bike", "mumbai", dummyAttributes(), 450000, dummySecondoryStatus());
        Inventory expectedInventory = getInventory(inventoryRequest);
        when(inventoryRepository.createInventory(inventoryCaptor.capture())).thenReturn("09d6afa5-c898-44a1-bddb-d40a4feeee81");

        String actualSku = inventoryService.createInventory(inventoryRequest);
        Inventory actualInventory = inventoryCaptor.getValue();

        assertThat(actualInventory)
                .usingRecursiveComparison()
                .ignoringFields("sku", "createdAt", "updatedAt", "id")
                .isEqualTo(expectedInventory);
        assertEquals(expectedSku, actualSku);
        verify(inventoryRepository, times(1)).createInventory(actualInventory);
    }

    @Test
    void ShouldThrowInvalidTypeException() {
        JsonNode attributes = dummyAttributes();
        ArrayList<SecondaryStatus> secondaryStatus = dummySecondoryStatus();
        InventoryRequest inventoryRequest = new InventoryRequest("mobile", "mumbai", attributes, 450000, secondaryStatus);
        String expected = "mobile is not supported";

        Exception actualError = Assertions.assertThrows(Exception.class, () -> inventoryService.createInventory(inventoryRequest));

        assertEquals(expected, actualError.getMessage());
    }

    @Test
    void ShouldReturnListOfInventories() {
        JsonNode attributes = dummyAttributes();
        ArrayList<SecondaryStatus> secondaryStatus = dummySecondoryStatus();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("sku")));
        Inventory inventory = getInventory(attributes, secondaryStatus);
        Page<Inventory> fetchedInventories = new PageImpl<>(List.of(inventory));
        List<InventoryResponse> expectedInventories = List.of(getInventoryResponse(attributes, secondaryStatus, inventory));
        when(inventoryRepository.fetchInventories(pageable)).thenReturn(fetchedInventories);

        List<InventoryResponse> actualInventories = inventoryService.getInventories(pageable);

        assertEquals(expectedInventories, actualInventories);

    }

    @Test
    void ShouldReturnInventory() throws InventoryNotFoundException {
        ArrayList<SecondaryStatus> secondaryStatus = dummySecondoryStatus();
        JsonNode attributes = dummyAttributes();
        Inventory inventory = getInventory(attributes, secondaryStatus);
        InventoryResponse expectedInventory = getInventoryResponse(attributes, secondaryStatus, inventory);
        when(inventoryRepository.findInventory("09d6afa5-c898-44a1-bddb-d40a4feeee81")).thenReturn(inventory);

        InventoryResponse actualInventory = inventoryService.getInventory("09d6afa5-c898-44a1-bddb-d40a4feeee81");

        assertEquals(expectedInventory, actualInventory);
    }

    @Test
    void ShouldUpdateStatus() throws Exception {
        ArrayList<SecondaryStatus> existingSecondaryStatus = dummySecondoryStatus();
        ArrayList<SecondaryStatus> NewSecondaryStatus = dummySecondoryStatus();
        String sku = "09d6afa5-c898-44a1-bddb-d40a4feeee81";
        Inventory inventory=getInventory(dummyAttributes(),existingSecondaryStatus);
        NewSecondaryStatus.add(new SecondaryStatus("legal","completed"));
        when(inventoryRepository.findInventory(sku)).thenReturn(inventory);

        inventoryService.updateStatus(sku,NewSecondaryStatus);

        verify(inventoryRepository, times(1)).saveInventory(inventory);
    }

    @Test
    void ShouldUpdateInventory() throws Exception {
        String sku = "09d6afa5-c898-44a1-bddb-d40a4feeee81";
        Map<String, Object> field = new HashMap<>();
        createUpdateField(field);
        Inventory inventory=getInventory(dummyAttributes(),dummySecondoryStatus());
        when(inventoryRepository.findInventory(sku)).thenReturn(inventory);

        inventoryService.patchInventory(sku,field);

        verify(inventoryRepository,times(1)).saveInventory(inventory);
    }

    private static ArrayList<SecondaryStatus> dummySecondoryStatus() {
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse", "in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit", "in-progress"));
        return secondaryStatus;
    }

    private static JsonNode dummyAttributes() {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        return attributes;
    }

    private static Inventory getInventory(JsonNode attributes, ArrayList<SecondaryStatus> secondaryStatus) {
        Inventory inventory = new Inventory("09d6afa5-c898-44a1-bddb-d40a4feeee81", "car", "Mumbai", "user", "user", attributes, 450000f, 0f, secondaryStatus);
        return inventory;
    }

    private static InventoryResponse getInventoryResponse(JsonNode attributes, ArrayList<SecondaryStatus> secondaryStatus, Inventory inventory) {
        return new InventoryResponse("09d6afa5-c898-44a1-bddb-d40a4feeee81", "car", "created", "Mumbai", inventory.getCreatedAt(), inventory.getUpdatedAt(), "user", "user", attributes, 450000, secondaryStatus);
    }

    private static void createUpdateField(Map<String, Object> field) throws JsonProcessingException {
        field.put("status", "procured");
        field.put("costPrice", 460000f);
        JsonNode attributesValue = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributesValue).put("color", "red");
        ((ObjectNode) attributesValue).put("year", 2021);
        field.put("attributes", attributesValue);
    }

    private static Inventory getInventory(InventoryRequest inventoryRequest) {
        Inventory expectedInventory = new Inventory(inventoryRequest.getType(), inventoryRequest.getLocation(), "user",
                "user", inventoryRequest.getAttributes(), inventoryRequest.getCostPrice(),
                inventoryRequest.getSecondaryStatus());
        return expectedInventory;
    }

}
