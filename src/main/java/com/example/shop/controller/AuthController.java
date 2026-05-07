package com.example.shop.controller;

import com.example.shop.dto.AuthRequest;
import com.example.shop.dto.AuthResponse;
import com.example.shop.dto.RegisterRequest;
import com.example.shop.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(
            @Valid @RequestBody RegisterRequest request
    ) {

        userService.register(request);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody AuthRequest request
    ) {

        return userService.login(request);
    }
}