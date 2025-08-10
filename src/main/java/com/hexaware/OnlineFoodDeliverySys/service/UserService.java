package com.hexaware.OnlineFoodDeliverySys.service;

import java.util.List;
import java.util.Optional;
import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;

public interface UserService {
    User addUser(UserDto dto);
    User updateUser(User user);
    User getByUserId(Long userId);
    String deleteByUserId(Long userId);
    List<User> getAllUsers();
    Optional<User> getByEmail(String email);
    List<User> getByRole(String role);
}
