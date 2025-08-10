package com.hexaware.OnlineFoodDeliverySys.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService service;

    @PostMapping("/insert")
    public User addUser(@RequestBody @Valid UserDto dto) {
        log.info("Adding user: {}", dto);
        return service.addUser(dto);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @GetMapping("/getbyid/{userId}")
    public User getByUserId(@PathVariable Long userId) {
        return service.getByUserId(userId);
    }

    @DeleteMapping("/deletebyid/{userId}")
    public String deleteByUserId(@PathVariable Long userId) {
        return service.deleteByUserId(userId);
    }

    @GetMapping("/getbyemail/{email}")
    public Optional<User> getByEmail(@PathVariable String email) {
        return service.getByEmail(email);
    }

    @GetMapping("/getbyrole/{role}")
    public List<User> getByRole(@PathVariable String role) {
        return service.getByRole(role);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }
}
