package com.hexaware.OnlineFoodDeliverySys.controller;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService service;

    @PostMapping("/insert")
    public User addUser(@RequestBody @Valid UserDto dto) {
        log.info("Received request to add user: {}", dto);
        return service.addUser(dto);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Received request to update user: {}", user);
        return service.updateUser(user);
    }

    @GetMapping("/getbyid/{userId}")
    public User getByUserId(@PathVariable Long userId) {
        log.info("Received request to fetch user with ID: {}", userId);
        return service.getByUserId(userId);
    }

    @DeleteMapping("/deletebyid/{userId}")
    public String deleteByUserId(@PathVariable Long userId) {
        log.info("Received request to delete user with ID: {}", userId);
        return service.deleteByUserId(userId);
    }

    @GetMapping("/getbyemail/{email}")
    public User getByEmail(@PathVariable String email) {
        log.info("Received request to fetch user by email: {}", email);
        return service.getByEmail(email);
    }

    @GetMapping("/getbyrole/{role}")
    public List<User> getByUserRole(@PathVariable String role) {
        log.info("Received request to fetch users by role: {}", role);
        return service.getByUserRole(role);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        log.info("Received request to fetch all users");
        return service.getAllUsers();
    }
}
