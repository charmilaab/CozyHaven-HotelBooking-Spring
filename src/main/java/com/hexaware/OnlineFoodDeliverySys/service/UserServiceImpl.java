package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.UserNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public User addUser(UserDto dto) {
        User u = new User();
        u.setUserId(dto.getUserId());
        u.setUserName(dto.getUserName());
        u.setEmail(dto.getEmail());
        u.setPassword(passwordEncoder.encode(dto.getPassword()));
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setUserRole(dto.getUserRole());
        u.setCreatedAt(LocalDateTime.now());
        return repo.save(u);
    }

    @Override
    public User updateUser(User user) {
        repo.findById(user.getUserId())
            .orElseThrow(() -> new UserNotFoundException("User not found: " + user.getUserId()));
        return repo.save(user);
    }

    @Override
    public User getByUserId(Long userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));
    }

    @Override
    public String deleteByUserId(Long userId) {
        User u = getByUserId(userId);
        repo.delete(u);
        return "User deleted successfully";
    }

    @Override
    public List<User> getAllUsers() { return repo.findAll(); }

    @Override
    public User getByEmail(String email) {
        return repo.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public List<User> getByUserRole(String role) { return repo.findByUserRole(role); }
}