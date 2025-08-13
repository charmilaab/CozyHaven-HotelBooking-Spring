package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.config.JwtUtil;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("token", token);
        return response;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        String token = jwtUtil.generateToken(authentication.getName());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        return response;
    }
}
