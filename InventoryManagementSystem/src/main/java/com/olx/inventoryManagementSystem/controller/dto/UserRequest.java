package com.olx.inventoryManagementSystem.controller.dto;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRequest {
    String email;
    String password;
}
