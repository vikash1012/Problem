package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAUserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
