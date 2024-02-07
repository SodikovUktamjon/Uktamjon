package com.uktamjon.sodikov.utils;

import com.uktamjon.sodikov.utils.BruteForceProtectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceProtectionServiceTest {

    private BruteForceProtectionService bruteForceProtectionService;

    @BeforeEach
    void setUp() {
        bruteForceProtectionService = new BruteForceProtectionService();
    }

    @Test
    void recordFailedLogin() {
        String username = "testUser";
        bruteForceProtectionService.recordFailedLogin(username);
        assertFalse(bruteForceProtectionService.isUserBlocked(username), "User should not be blocked after one failed attempt");
    }

    @Test
    void recordMultipleFailedLoginsAndCheckBlocked() {
        String username = "blockedUser";
        for (int i = 0; i < BruteForceProtectionService.MAX_ATTEMPTS; i++) {
            bruteForceProtectionService.recordFailedLogin(username);
        }
        assertTrue(bruteForceProtectionService.isUserBlocked(username), "User should be blocked after reaching maximum attempts");
    }

    @Test
    void recordFailedLoginAndCheckUnblockedAfterLockoutDuration() throws InterruptedException {
        String username = "lockoutUser";
        bruteForceProtectionService.recordFailedLogin(username);
        assertFalse(bruteForceProtectionService.isUserBlocked(username), "User should not be blocked immediately");
        Thread.sleep(BruteForceProtectionService.LOCKOUT_DURATION+1000);
        assertFalse(bruteForceProtectionService.isUserBlocked(username), "User should be unblocked after lockout duration");
    }

}
