package com.olx.inventoryManagementSystem.utils;

import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class LoadByUsername implements UserDetailsService {
    UserRepository userRepository;

    @Autowired
    public LoadByUsername(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.userExistByEmail(username);
        // TODO: user respository should return Optional<User>
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                new ArrayList<>());
    }

}
