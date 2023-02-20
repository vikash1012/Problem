package com.olx.inventoryManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;
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
    private float soldAt;
    private Object secondaryStatus;

    public Inventory(String sku, String type, String location, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, Object attributes, float costPrice, Object secondaryStatus) {
        this.sku = sku;
        this.type = type;
        //this.status = status;
        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.attributes = attributes;
        this.costPrice = costPrice;
        this.secondaryStatus = secondaryStatus;
    }
}
