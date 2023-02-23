package com.olx.inventoryManagementSystem.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.olx.inventoryManagementSystem.controller.dto.InventoryResponse;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.olx.inventoryManagementSystem.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean({InventoryService.class})
@WebMvcTest(controllers = InventoryController.class)
class InventoryControllerTest {
    @MockBean
    InventoryService inventoryService;

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        InventoryController inventoryController = new InventoryController(inventoryService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(inventoryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void ShouldFetchListOfInventoriesFromGetInventoriesApi() throws Exception {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        List<InventoryResponse> expectedInventories = List.of(new InventoryResponse("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "created", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus));
        when(inventoryService.getInventories(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("sku"))))).thenReturn(expectedInventories);
        String expectedResponse = "[{\"sku\":\"d59fdbd5-0c56-4a79-8905-6989601890be\",\"type\":\"car\",\"status\":\"created\",\"location\":\"Mumbai\",\"createdAt\":[2023,2,21,22,59],\"updatedAt\":[2023,2,21,22,59],\"createdBy\":\"user\",\"updatedBy\":\"user\",\"attributes\":{\"vin\":\"AP31CM9873\",\"make\":\"Tata\",\"model\":\"Nexon\"},\"costPrice\":450000.0,\"secondaryStatus\":[{\"name\":\"warehouse\",\"status\":\"in-repair\"},{\"name\":\"transit\",\"status\":\"in-progress\"}]}]";

        MockHttpServletRequestBuilder request = get("/inventories");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void ShouldFetchInventoryFromGetApiForParticularSku() throws Exception {
        JsonNode attributes = new ObjectMapper().createObjectNode();
        ((ObjectNode) attributes).put("vin", "AP31CM9873");
        ((ObjectNode) attributes).put("make", "Tata");
        ((ObjectNode) attributes).put("model", "Nexon");
        ArrayList<SecondaryStatus> secondaryStatus = new ArrayList<>();
        secondaryStatus.add(new SecondaryStatus("warehouse","in-repair"));
        secondaryStatus.add(new SecondaryStatus("transit","in-progress"));
        when(inventoryService.getInventory("d59fdbd5-0c56-4a79-8905-6989601890be")).thenReturn(new InventoryResponse("d59fdbd5-0c56-4a79-8905-6989601890be", "car", "created", "Mumbai", LocalDateTime.of(2023, 2, 21, 22, 59), LocalDateTime.of(2023, 2, 21, 22, 59), "user", "user", attributes, 450000, secondaryStatus));
        String expectedResponse = "{\"sku\":\"d59fdbd5-0c56-4a79-8905-6989601890be\",\"type\":\"car\",\"status\":\"created\",\"location\":\"Mumbai\",\"createdAt\":[2023,2,21,22,59],\"updatedAt\":[2023,2,21,22,59],\"createdBy\":\"user\",\"updatedBy\":\"user\",\"attributes\":{\"vin\":\"AP31CM9873\",\"make\":\"Tata\",\"model\":\"Nexon\"},\"costPrice\":450000.0,\"secondaryStatus\":[{\"name\":\"warehouse\",\"status\":\"in-repair\"},{\"name\":\"transit\",\"status\":\"in-progress\"}]}";

        MockHttpServletRequestBuilder requestWithId = get("/inventories/d59fdbd5-0c56-4a79-8905-6989601890be");

        this.mockMvc.perform(requestWithId)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

    }

}