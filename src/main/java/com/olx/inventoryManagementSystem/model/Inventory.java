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

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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
    private Object attributes; // why not json node!!
    @Column(name = "cost_price")
    private float costPrice;
    @Column(name = "sold_at")
    private float soldAt;
    @Column(name = "secondary_status", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private ArrayList<SecondaryStatus> secondaryStatus;

    public Inventory(String sku, String type, String location, LocalDateTime createdAt,
                     LocalDateTime updatedAt, String createdBy, String updatedBy, Object attributes, float costPrice,
                     ArrayList<SecondaryStatus> secondaryStatus) {
        this.sku = sku;
        this.type = type;
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
