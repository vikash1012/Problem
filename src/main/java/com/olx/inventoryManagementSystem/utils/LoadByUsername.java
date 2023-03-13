package com.olx.inventoryManagementSystem.utils;

import com.olx.inventoryManagementSystem.exceptions.UserNameNotFoundException;
import com.olx.inventoryManagementSystem.model.User;
import com.olx.inventoryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class LoadByUsername implements UserDetailsService {
    UserRepository userRepository;

    @Autowired
    public LoadByUsername(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.userExistByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User is Invalid");
        }

        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                new ArrayList<>());
    }

}
