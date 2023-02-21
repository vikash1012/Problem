package com.olx.inventoryManagementSystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class InventoryRequest<T> {
    String type;
    String location;
    float costPrice;
    T attributes;

    ArrayList<SecondaryStatus> secondaryStatus;
}
