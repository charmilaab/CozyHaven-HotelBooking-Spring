package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Override
    public User addUser(UserDto dto) {
        User user = new User();
        user.setUserId(dto.getUserId());
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setUserRole(dto.getUserRole());
        user.setCreatedAt(LocalDateTime.now());
        return repo.save(user);
    }

    @Override
    public User updateUser(User user) {
        return repo.save(user);
    }

    @Override
    public User getByUserId(Long userId) {
        return repo.findById(userId).orElse(null);
    }

    @Override
    public String deleteByUserId(Long userId) {
        repo.deleteById(userId);
        return "User deleted successfully";
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getByEmail(String email) {
        return repo.findUserByEmail(email).orElse(null); 
    }

    @Override
    public List<User> getByUserRole(String role) { 
        return repo.findByUserRole(role);
    }
}
