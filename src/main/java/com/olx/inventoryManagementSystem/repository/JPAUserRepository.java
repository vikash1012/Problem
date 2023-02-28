package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPAUserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
