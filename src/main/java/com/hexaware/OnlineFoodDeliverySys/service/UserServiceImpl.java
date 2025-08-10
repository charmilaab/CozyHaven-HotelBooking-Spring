package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.UserNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User addUser(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserRole(dto.getUserRole());
        user.setCreatedAt(dto.getCreatedAt());

        log.info("Adding new user: {}", dto);
        return userRepo.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (!userRepo.existsById(user.getUserId())) {
            throw new UserNotFoundException("User not found with ID: " + user.getUserId());
        }
        return userRepo.save(user);
    }

    @Override
    public User getByUserId(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public String deleteByUserId(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new UserNotFoundException("Cannot delete. User not found with ID: " + userId);
        }
        userRepo.deleteById(userId);
        return "User deleted successfully";
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public List<User> getByRole(String role) {
        // Updated to match field name 'userRole'
        return userRepo.findByUserRole(role);
    }
}
