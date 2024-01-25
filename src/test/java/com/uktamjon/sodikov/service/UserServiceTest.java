package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.repository.UserRepository;
import com.uktamjon.sodikov.services.UserService;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordGeneratorService passwordGenerator;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordGenerator = mock(PasswordGeneratorService.class);
        userService = new UserService(userRepository, passwordGenerator);
    }


    @Test
    public void testCreateUser_UniqueUsername() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        List<User> existingUsers = new ArrayList<>();
        when(userRepository.findAllByUsernameContains("John.Doe")).thenReturn(existingUsers);
        when(passwordGenerator.generateRandomPassword(10)).thenReturn("randomPassword");
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        System.out.println(createdUser.getUsername());
        assertEquals("John.Doe", createdUser.getUsername());
    }


    @Test
    public void testCreateUser_NonUniqueUsername() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        List<User> existingUsers = new ArrayList<>();
        existingUsers.add(new User());
        when(userRepository.findAllByUsernameContains("John.Doe")).thenReturn(existingUsers);
        when(passwordGenerator.generateRandomPassword(10)).thenReturn("randomPassword");
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertEquals("John.Doe1", createdUser.getUsername());
    }


    @Test
    public void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User());
        expectedUsers.add(new User());
        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> retrievedUsers = userService.getAllUsers();
        assertEquals(expectedUsers, retrievedUsers);
    }

    @Test
    public void testGetUserById_UserExists() {
        int userId = 1;
        User expectedUser = new User();
        expectedUser.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        User retrievedUser = userService.getUserById(userId);
        assertEquals(expectedUser, retrievedUser);
    }

    @Test
    public void testGetUserById_UserDoesNotExist() {
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    public void testUpdateUser_UserExists() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.updateUser(user);
    }

    @Test
    public void testUpdateUser_UserDoesNotExist() {
        User user = new User();
        user.setId(1);
        when(userRepository.existsById(user.getId())).thenReturn(false);
        assertThrows(UsernameNotFoundException.class, () -> userService.updateUser(user));
    }

    @Test
    public void testDeleteUserById() {
        int userId = 1;
        userService.deleteUserById(userId);
    }
}
