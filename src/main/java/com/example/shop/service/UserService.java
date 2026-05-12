package com.example.shop.service;

import com.example.shop.dto.AuthRequest;
import com.example.shop.dto.AuthResponse;
import com.example.shop.dto.RegisterRequest;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {

        if (userRepository.findByUsername(
                request.getUsername()
        ).isPresent()) {

            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(
                        encoder.encode(request.getPassword())
                )
                .name(request.getName())
                .address(request.getAddress())
                .build();

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(
                request.getUsername()
        ).orElseThrow(() ->
                new RuntimeException("Invalid credentials")
        );

        if (!encoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(
                user.getUsername()
        );

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getName(),
                user.getAddress()
        );
    }
}