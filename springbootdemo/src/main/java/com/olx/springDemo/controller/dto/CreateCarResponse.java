package com.olx.springDemo.controller.dto;


public class CreateCarResponse {
    Long id;

    public CreateCarResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
