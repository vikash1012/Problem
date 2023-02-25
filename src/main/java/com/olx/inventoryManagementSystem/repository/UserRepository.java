package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    JPAUserRepository jpaUserRepository;

    @Autowired
    public UserRepository(JPAUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public boolean userExistByEmail(String email) {
        return jpaUserRepository.findByEmail(email) != null;
    }

    public String createUser(User user) {

        User savedUser = this.jpaUserRepository.save(user);
        return "User Registered Successfully";
    }
}
