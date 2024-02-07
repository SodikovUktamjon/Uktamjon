package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.repository.UserRepository;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordGeneratorService passwordGenerator;


    public User createUser(User user) {
        log.info("Creating user {}", user);
        user.setUsername(generateUserName(user.getFirstName(), user.getLastName()));
        String s = passwordGenerator.generateRandomPassword(10);
        user.setPassword(passwordGenerator.encryptPassword(s));
        log.info("User created {}", user);
        User save = userRepository.save(user);
        save.setPassword(s);
        return save;
    }

    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    public User getUserById(int userId) {
        log.info("Getting user by id {}", userId);
        return userRepository.findById(userId).orElse(null);
    }

    public void updateUser(User user) {
        log.info("Updating user {}", user);
        User userById = getUserById(user.getId());

        if (user.getPassword() != null) {
            user.setPassword(passwordGenerator.encryptPassword(user.getPassword()));
        }
        user.setUsername(userById.getUsername());
        userRepository.save(user);
        log.info("User updated {}", user);
    }

    public void deleteUserById(int userId) {
        userRepository.deleteById(userId);
        log.info("User deleted {}", userId);
    }

    public User getUserByUsername(String username) {
        log.info("Getting user by username {}", username);
        return userRepository.findByUsername(username);
    }

    public boolean changePassword(String password, String username) {
        User user = getUserByUsername(username);
        if (checkingUserAndPasswordMatching(password, user)) return false;
        user.setPassword(password);
        updateUser(user);
        return true;
    }

    public boolean changePassword(String password, int id) {
        log.info("Changing password for user with id {}", id);
        User user = getUserById(id);
        if (checkingUserAndPasswordMatching(password, user)) return false;
        user.setPassword(passwordGenerator.encryptPassword(password));
        updateUser(user);
        log.info("Password changed for user with id {}", id);
        return true;
    }

    private static boolean checkingUserAndPasswordMatching(String password, User user) {
        if (user == null) {
            log.error("User not found ");
            return true;
        }
        if (!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", password)) {
            log.error("Password is not valid");
            return true;
        }
        return false;
    }

    public boolean activateAndDeactivate(String username) {
        log.info("Changing active status for user {}", username);
        User user = getUserByUsername(username);
        if (user == null) {
            log.error("User not found by username: {}", username);
            return false;
        }
        user.setActive(!user.isActive());
        updateUser(user);
        log.info("Active status changed for user {}", username);
        return true;
    }

    public boolean activateAndDeactivate(int id) {
        log.info("Changing active status for user {}", id);
        User user = getUserById(id);
        if (user == null) {
            log.error("User not found by user id: {}", id);
            return false;
        }
        user.setActive(!user.isActive());
        updateUser(user);
        log.info("Active status changed for user {}", id);
        return true;
    }

    public List<User> findByCriteria(String username) {
        log.info("Getting users by criteria {}", username);
        return userRepository.findAllByUsernameContains(username);
    }

    public void deleteByUsername(String username) {
        if (getUserByUsername(username) == null) {
            log.error("User not found by username: {}", username);
            return;
        }
        log.info("Deleting user by username {}", username);
        userRepository.deleteByUsername(username);
    }

    public  String generateUserName(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;

        boolean usernameExists = userRepository.existsByUsername(baseUsername);

        String generatedUsername = usernameExists ? baseUsername + getUserNameSuffix() : baseUsername;

        log.info("Generated username: {}", generatedUsername);
        return generatedUsername;
    }
    private static Long userNameSuffix = 0L;

    public static Long getUserNameSuffix() {
        userNameSuffix++;
        log.debug("Generated User Name Suffix: {}", userNameSuffix);
        return userNameSuffix;
    }


}
