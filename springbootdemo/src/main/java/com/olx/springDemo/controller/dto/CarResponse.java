package com.olx.springDemo.controller.dto;

import java.util.Objects;

public class CarResponse {
    private Long id;

    private String make;
    private String model;
    private String color;

    public CarResponse(Long id, String make, String model, String color) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarResponse)) return false;
        CarResponse that = (CarResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getMake(), that.getMake()) && Objects.equals(getModel(), that.getModel()) && Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMake(), getModel(), getColor());
    }
}
