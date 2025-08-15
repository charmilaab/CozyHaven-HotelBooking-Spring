package com.hexaware.hotelbooking.controller;

import com.hexaware.hotelbooking.dto.UserDto;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired private UserService service;

    @PostMapping("/insert")
    public User addUser(@RequestBody @Valid UserDto dto) {
        log.info("Add user {}", dto.getEmail());
        return service.addUser(dto);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody @Valid User user) {
        return service.updateUser(user);
    }

    @GetMapping("/getbyid/{userId}")
    public User getByUserId(@PathVariable Long userId) { return service.getByUserId(userId); }

    @DeleteMapping("/deletebyid/{userId}")
    public String deleteByUserId(@PathVariable Long userId) { return service.deleteByUserId(userId); }

    @GetMapping("/getbyemail/{email}")
    public User getByEmail(@PathVariable String email) { return service.getByEmail(email); }

    @GetMapping("/getbyrole/{role}")
    public List<User> getByRole(@PathVariable String role) { return service.getByUserRole(role); }

    @GetMapping("/getall")
    public List<User> getAllUsers() { return service.getAllUsers(); }
}