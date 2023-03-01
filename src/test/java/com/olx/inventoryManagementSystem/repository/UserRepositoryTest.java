package com.olx.inventoryManagementSystem.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.olx.inventoryManagementSystem.controller.dto.SecondaryStatus;
import com.olx.inventoryManagementSystem.model.Inventory;
import com.olx.inventoryManagementSystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    UserRepository userRepository;
    @Mock
    JPAUserRepository jpaUserRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository(jpaUserRepository);
    }

    @Test
    void ShouldReturnUserWhenEmailIsGiven() {
        String email = "user@email.com";
        User expectedUser = new User("user@email.com", "124");
        when(jpaUserRepository.findByEmail(email)).thenReturn(expectedUser);

        User actualUser = userRepository.userExistByEmail(email);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void ShouldReturnStringWhenUserIsCreated() {
        String expected = "User Registered Successfully";
        User user = new User("user@email.com", "124");
        when(jpaUserRepository.save(user)).thenReturn(null);

        String actual = userRepository.createUser(user);

        assertEquals(expected, actual);
    }

}