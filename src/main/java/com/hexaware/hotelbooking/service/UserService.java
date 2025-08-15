package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.UserDto;
import com.hexaware.hotelbooking.entities.User;

import java.util.*;

public interface UserService {
    User addUser(UserDto dto);
    User updateUser(User user);
    User getByUserId(Long userId);
    String deleteByUserId(Long userId);
    List<User> getAllUsers();
    User getByEmail(String email);
    List<User> getByUserRole(String role);
}