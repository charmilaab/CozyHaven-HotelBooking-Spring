package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;

import java.util.List;

public interface UserService {
    User addUser(UserDto dto);
    User updateUser(User user);
    User getByUserId(Long userId);
    String deleteByUserId(Long userId);
    List<User> getAllUsers();
    User getByEmail(String email);
    List<User> getByUserRole(String role);

}
