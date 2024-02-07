package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.repository.UserRepository;
import com.uktamjon.sodikov.services.UserService;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordGeneratorService passwordGenerator;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUser() {
        User testUser = User.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        when(passwordGenerator.generateRandomPassword(10)).thenReturn("randomPassword");
        when(passwordGenerator.encryptPassword("randomPassword")).thenReturn("hashedPassword");
        when(userRepository.save(Mockito.any())).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertEquals("John.Doe", createdUser.getUsername());
        assertNotNull(createdUser.getPassword());
        assertFalse(createdUser.isActive());
        verify(userRepository, times(1)).save(Mockito.any());
    }

    @Test
    public void testGenerateUserName() {
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = firstName + "." + lastName;

        when(userRepository.existsByUsername(baseUsername)).thenReturn(true);

        String generatedUsername = userService.generateUserName(firstName, lastName);

        assertEquals("John.Doe1", generatedUsername);
        verify(userRepository, times(1)).existsByUsername(baseUsername);
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(User.builder().firstName("John").lastName("Doe").build()));

        List<User> allUsers = userService.getAllUsers();

        assertEquals(1, allUsers.size());
        verify(userRepository, times(1)).findAll();
    }


    @Test
    public void testGetUserById() {
        User testUser = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        User retrievedUser = userService.getUserById(1);

        assertNotNull(retrievedUser);
        assertEquals(testUser, retrievedUser);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateUser() {
        User testUser = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(Mockito.any())).thenReturn(testUser);

        userService.updateUser(testUser);

        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(Mockito.any());
    }

    @Test
    public void testUpdateUserWithPassword() {
        User testUser = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .password("old_password")
                .build();
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        when(userRepository.save(Mockito.any())).thenReturn(testUser);

        userService.updateUser(testUser);
        verify(userRepository, times(1)).findById(testUser.getId());
        verify(passwordGenerator,times(1)).encryptPassword(Mockito.any());
        verify(userRepository, times(1)).save(Mockito.any());
    }


    @Test
    public void testDeleteUserById() {
        int userId = 1;

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testGetUserByUsername() {
        User testUser = User.builder()
                .username("John.Doe")
                .build();

        when(userRepository.findByUsername("John.Doe")).thenReturn(testUser);

        User retrievedUser = userService.getUserByUsername("John.Doe");

        assertNotNull(retrievedUser);
        assertEquals(testUser, retrievedUser);
        verify(userRepository, times(1)).findByUsername("John.Doe");
    }


    @Test
    void testChangePasswordWithValidPasswordAndUsername() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("old_password");

        when(userRepository.findByUsername("john_doe")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertTrue(userService.changePassword("P@ssw0rd", "john_doe"));
    }

    @Test
    void testChangePasswordWithInvalidPassword() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("old_password");

        when(userRepository.findByUsername("john_doe")).thenReturn(user);

        assertFalse(userService.changePassword("weakpassword", "john_doe"));
        assertEquals("old_password", user.getPassword());
    }

    @Test
    void testActivateAndDeactivateByUsername() {
        User user = new User();
        user.setUsername("john_doe");
        user.setActive(true);

        when(userRepository.findByUsername("john_doe")).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertTrue(userService.activateAndDeactivate("john_doe"));
        assertFalse(user.isActive());
    }

    @Test
    void testDeleteByUsername() {
        User user = new User();
        user.setUsername("john_doe");

        when(userRepository.findByUsername("john_doe")).thenReturn(user);

        userService.deleteByUsername("john_doe");

        verify(userRepository, times(1)).deleteByUsername("john_doe");
    }
}
