package com.olx.inventoryManagementSystem.controller.dto;

import com.olx.inventoryManagementSystem.model.CarAttributes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {
    String type;
    String location;
    float costPrice;
    CarAttributes carAttributes;
    SecondaryStatus secondaryStatus;

    public CarAttributes getCarAttributes() {
        return carAttributes;
    }

    public SecondaryStatus getSecondaryStatus() {
        return secondaryStatus;
    }

    public InventoryRequest(String type, String location, float costPrice, CarAttributes carAttributes, SecondaryStatus secondaryStatus) {
        this.type = type;
        this.location = location;
        this.costPrice = costPrice;
        this.carAttributes = carAttributes;
        this.secondaryStatus = secondaryStatus;
    }
}
