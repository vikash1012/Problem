package com.olx.inventoryManagementSystem.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Component
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private int id;

    private String sku;

    private String type;

    private String status = "created";

    private String location;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "attributes", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Object attributes;

    @Column(name = "cost_price")
    private float costPrice;

    @Column(name = "sold_at")
    private float soldAt;

    @Column(name = "secondary_status", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private ArrayList<SecondaryStatus> secondaryStatus;

    public Inventory(String type, String location, String createdBy, String updatedBy, Object attributes,
                     float costPrice, ArrayList<SecondaryStatus> secondaryStatus) {
        UUID sku = UUID.randomUUID();
        LocalDateTime localDateTime = LocalDateTime.now();
        this.sku = sku.toString();
        this.type = type;
        this.location = location;
        this.createdAt = localDateTime;
        this.updatedAt = localDateTime;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.attributes = attributes;
        this.costPrice = costPrice;
        this.secondaryStatus = secondaryStatus;
    }

    public Inventory(String sku, String type, String location, String createdBy, String updatedBy, Object attributes, float costPrice, float soldAt, ArrayList<SecondaryStatus> secondaryStatus) {
        this.sku = sku;
        this.id = 7;
        this.type = type;
        this.location = location;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.attributes = attributes;
        this.costPrice = costPrice;
        this.soldAt = soldAt;
        this.secondaryStatus = secondaryStatus;
    }

    public void UpdateStatus(Inventory inventory, ArrayList<SecondaryStatus> secondaryStatus) {
        ArrayList<SecondaryStatus> inventorySecondaryStatus = inventory.getSecondaryStatus();
        for (SecondaryStatus statuses : secondaryStatus) {
            if (!inventorySecondaryStatus.contains(statuses)) {
                this.addStatus(inventory, statuses);
                continue;
            }
            this.changeStatus(inventory, statuses);
        }
    }

    private void changeStatus(Inventory inventory, SecondaryStatus statuses) {
        ArrayList<SecondaryStatus> statusArrayList = inventory.getSecondaryStatus();
        for (SecondaryStatus status : statusArrayList) {
            if (status.getName().equals(statuses.getName())) {
                status.setStatus(statuses.getStatus());
            }
        }
        inventory.setSecondaryStatus(statusArrayList);
    }

    private void addStatus(Inventory inventory, SecondaryStatus statuses) {
        ArrayList<SecondaryStatus> statusArrayList = inventory.getSecondaryStatus();
        statusArrayList.add(statuses);
    }
}
