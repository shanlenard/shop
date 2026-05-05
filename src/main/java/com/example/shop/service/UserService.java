package com.example.shop.service;

import org.springframework.stereotype.Service;

import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }

        return user;
    }
    
}
