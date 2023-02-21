package com.olx.inventoryManagementSystem.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type",visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CarDto.class, name = "car"),
        @JsonSubTypes.Type(value = BikeDto.class, name = "bike")
})
public class InventoryRequest {
    String type;
    String location;
    float costPrice;
    ArrayList<SecondaryStatus> secondaryStatus;
}
