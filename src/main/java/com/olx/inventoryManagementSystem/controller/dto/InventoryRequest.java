package com.olx.inventoryManagementSystem.controller.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class InventoryRequest {

    String type;
    String location;
    JsonNode attributes;
    float costPrice;
    ArrayList<SecondaryStatus> secondaryStatus;

}
