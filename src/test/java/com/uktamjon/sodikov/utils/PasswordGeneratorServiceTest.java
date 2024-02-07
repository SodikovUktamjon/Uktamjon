package com.uktamjon.sodikov.utils;

import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorServiceTest {

    @Test
    public void testGenerateRandomPassword_LengthOne() {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
        String generatedPassword = passwordGeneratorService.generateRandomPassword(1);

        assertEquals(1, generatedPassword.length());
    }

    @Test
    public void testGenerateRandomPassword_LengthTen() {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();
        String generatedPassword = passwordGeneratorService.generateRandomPassword(10);

        assertEquals(10, generatedPassword.length());
    }

    @Test
    public void testGenerateRandomPassword_LengthZero() {
        PasswordGeneratorService passwordGeneratorService = new PasswordGeneratorService();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                passwordGeneratorService.generateRandomPassword(0)
        );

        assertEquals("Length must be at least 1", exception.getMessage());
    }


    @Test
    public void testEncryptPassword() {
        PasswordGeneratorService encryptionService = new PasswordGeneratorService();

        String password = "password123";
        String encryptedPassword = encryptionService.encryptPassword(password);

        assertNotNull(encryptedPassword);
    }

    @Test
    public void testCheckPassword() {
        PasswordGeneratorService encryptionService = new PasswordGeneratorService();

        String password = "password123";
        String storedEncryptedPassword = encryptionService.encryptPassword(password);
        assertFalse(encryptionService.checkPassword(password, storedEncryptedPassword));
        assertFalse(encryptionService.checkPassword("incorrect_password", storedEncryptedPassword));
    }
}
