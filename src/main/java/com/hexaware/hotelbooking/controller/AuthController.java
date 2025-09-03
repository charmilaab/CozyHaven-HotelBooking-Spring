package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.config.JwtUtil;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") 
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // ================= REGISTER =================
    @PostMapping("/register")
    public AuthResponse register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        return new AuthResponse("User registered successfully", null);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public AuthResponse login(@RequestBody User loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), // make sure this matches your UserDetailsService
                            loginRequest.getPassword()
                    )
            );

            String token = jwtUtil.generateToken(authentication.getName());
            return new AuthResponse("Login successful", token);

        } catch (BadCredentialsException ex) {
            return new AuthResponse("Invalid email or password", null);
        }
    }

    // ================= AUTH RESPONSE DTO =================
    public static class AuthResponse {
        private String message;
        private String token;

        public AuthResponse(String message, String token) {
            this.message = message;
            this.token = token;
        }

        public String getMessage() { return message; }
        public String getToken() { return token; }
        public void setMessage(String message) { this.message = message; }
        public void setToken(String token) { this.token = token; }
    }
}
