package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.config.JwtUtil;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.repository.UserRepository;
import com.hexaware.hotelbooking.exceptions.UserNotFoundException;

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
        // hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);

        // ✅ return success message only (no token)
        return new AuthResponse("User registered successfully", null, saved.getUserRole(), saved);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public AuthResponse login(@RequestBody User loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // fetch user from DB
            User user = userRepository.findUserByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            // generate JWT
            String token = jwtUtil.generateToken(authentication.getName());

            // return token + role + user
            return new AuthResponse("Login successful", token, user.getUserRole(), user);

        } catch (BadCredentialsException ex) {
            return new AuthResponse("Invalid email or password", null, null, null);
        }
    }

    // ================= AUTH RESPONSE DTO =================
    public static class AuthResponse {
        private String message;
        private String token;
        private String role;
        private User user;

        public AuthResponse(String message, String token, String role, User user) {
            this.message = message;
            this.token = token;
            this.role = role;
            this.user = user;
        }

        public String getMessage() { return message; }
        public String getToken() { return token; }
        public String getRole() { return role; }
        public User getUser() { return user; }

        public void setMessage(String message) { this.message = message; }
        public void setToken(String token) { this.token = token; }
        public void setRole(String role) { this.role = role; }
        public void setUser(User user) { this.user = user; }
    }
}
