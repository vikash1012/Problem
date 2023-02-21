package com.olx.inventoryManagementSystem.model;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;

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
    private String status="created";
    private String location;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    @Column(name="created_by")
    private String createdBy;
    @Column(name="updated_by")
    private String updatedBy;
    @Column(name="attribute",columnDefinition = "json")
    private String attribute;
    @Column(name="cost_price")
    private float costPrice;
    @Column(name="sold_at")
    private float soldAt;
    @Column(name="secondary_status",columnDefinition = "json")
    private  String secondaryStatus;

    public Inventory(String sku, String type, String location, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, String attribute, float costPrice, String secondaryStatus) {
        this.sku = sku;
        this.type = type;

        this.location = location;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.attribute = attribute;
        this.costPrice = costPrice;
        this.secondaryStatus = secondaryStatus;
    }
}
