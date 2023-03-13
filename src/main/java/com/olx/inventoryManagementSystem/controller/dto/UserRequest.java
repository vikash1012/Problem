package com.olx.inventoryManagementSystem.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRequest {

    @NotBlank
    String email;

    @NotBlank
    String password;
}
