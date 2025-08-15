package com.hexaware.hotelbooking.service;

import com.hexaware.hotelbooking.dto.UserDto;
import com.hexaware.hotelbooking.entities.User;
import com.hexaware.hotelbooking.repository.UserRepository;
import com.hexaware.hotelbooking.service.UserService;
import com.hexaware.hotelbooking.service.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDto();
        userDto.setUserName("Charmila");
        userDto.setEmail("charmila@example.com");
        userDto.setPassword("Password@123");
        userDto.setPhoneNumber("9876543210");
        userDto.setUserRole("CUSTOMER");

        user = new User();
        user.setUserId(1L);
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword("hashedPassword");
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserRole(userDto.getUserRole());
    }

    @Test
    void testAddUser() {
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User saved = userService.addUser(userDto);

        assertNotNull(saved);
        assertEquals("Charmila", saved.getUserName());
        assertEquals("hashedPassword", saved.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testGetByUserId() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User found = userService.getByUserId(1L);

        assertNotNull(found);
        assertEquals(1L, found.getUserId());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> list = userService.getAllUsers();

        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("NewPass")).thenReturn("hashedNewPass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updateUser = new User();
        updateUser.setUserId(1L);
        updateUser.setUserName("Charmila Updated");
        updateUser.setEmail("charmila_updated@example.com");
        updateUser.setPassword("NewPass");
        updateUser.setPhoneNumber("9999999999");
        updateUser.setUserRole("CUSTOMER");

        User updated = userService.updateUser(updateUser);

        assertEquals("Charmila Updated", updated.getUserName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteByUserId() {
        doNothing().when(userRepository).deleteById(1L);

        String result = userService.deleteByUserId(1L);

        assertEquals("Deleted user with id 1", result);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
