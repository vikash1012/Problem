package com.olx.inventoryManagementSystem.repository;

import com.olx.inventoryManagementSystem.exceptions.UserNameNotFoundException;
import com.olx.inventoryManagementSystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    JPAUserRepository jpaUserRepository;

    @Autowired
    public UserRepository(JPAUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    public Optional<User> userExistByEmail(String email){
         return this.jpaUserRepository.findByEmail(email);


    }

    public String createUser(User user) {
        return this.jpaUserRepository.save(user).getEmail();
    }
}
