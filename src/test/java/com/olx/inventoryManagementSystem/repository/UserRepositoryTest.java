package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        Optional<User> expectedUser = Optional.of(new User("user@email.com", "124"));
        when(jpaUserRepository.findByEmail(email)).thenReturn(expectedUser);
        Optional<User> actualUser = userRepository.userExistByEmail(email);

        assertEquals(expectedUser, actualUser);
        verify(jpaUserRepository, times(1)).findByEmail(email);
    }

    @Test
    void ShouldReturnStringWhenUserIsCreated() {
        String expected = "user@email.com";
        User user = new User("user@email.com", "124");
        when(jpaUserRepository.save(user)).thenReturn(user);

        String actual = userRepository.createUser(user);

        assertEquals(expected, actual);
        verify(jpaUserRepository,times(1)).save(user);
    }

}