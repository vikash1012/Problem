package com.olx.inventoryManagementSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class InventoryResponse {
    private String sku;
    private String type;
    private String status;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Object attributes;
    private float costPrice;
    private ArrayList<SecondaryStatus> secondaryStatus;
}
