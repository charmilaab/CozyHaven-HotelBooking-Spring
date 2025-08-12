package com.hexaware.OnlineFoodDeliverySys.service;

import com.hexaware.OnlineFoodDeliverySys.dto.UserDto;
import com.hexaware.OnlineFoodDeliverySys.entities.User;
import com.hexaware.OnlineFoodDeliverySys.exceptions.UserNotFoundException;
import com.hexaware.OnlineFoodDeliverySys.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserServiceImpl service;

    private User user;
    private UserDto dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setUserName("Charu");
        user.setEmail("charu@example.com");
        user.setPassword("Pass@123");
        user.setPhoneNumber("9876543210");
        user.setUserRole("CUSTOMER");
        user.setCreatedAt(LocalDateTime.now());

        dto = new UserDto();
        dto.setUserId(1L);
        dto.setUserName("Charu");
        dto.setEmail("charu@example.com");
        dto.setPassword("Pass@123");
        dto.setPhoneNumber("9876543210");
        dto.setUserRole("CUSTOMER");
    }

    @Test
    void testAddUser() {
        when(repo.save(any(User.class))).thenReturn(user);
        User saved = service.addUser(dto);
        assertEquals("Charu", saved.getUserName());
    }

    @Test
    void testGetByUserId_Found() {
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        assertEquals("Charu", service.getByUserId(1L).getUserName());
    }

    @Test
    void testGetByUserId_NotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> service.getByUserId(99L));
    }

    @Test
    void testGetAllUsers() {
        when(repo.findAll()).thenReturn(Arrays.asList(user));
        assertEquals(1, service.getAllUsers().size());
    }

    @Test
    void testDeleteByUserId() {
        when(repo.findById(1L)).thenReturn(Optional.of(user));
        String result = service.deleteByUserId(1L);
        assertEquals("User deleted successfully", result);
    }
}
