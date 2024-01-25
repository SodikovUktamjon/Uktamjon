package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.config.JwtUtils;
import com.uktamjon.sodikov.config.UserDetails;
import com.uktamjon.sodikov.config.UserDetailsService;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.enums.TokenType;
import com.uktamjon.sodikov.reponse.RefreshTokenRequest;
import com.uktamjon.sodikov.reponse.TokenResponse;
import com.uktamjon.sodikov.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserDetailsServiceTest {

    @Mock
    private UserRepository authUserRepository;

    @Mock
    private JwtUtils jwtUtils;


    @InjectMocks
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername() {
        String username = "testUser";
        User authUser = new User();
        when(authUserRepository.findByUsername(username)).thenReturn(authUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals(authUser, userDetails.getAuthUser(), "User in UserDetails should match");
    }

    @Test
    void loadUserByUsernameNotFound() {
        String username = "nonexistentUser";
        when(authUserRepository.findByUsername(username)).thenReturn(null);

        try {
            assertThrows(NullPointerException.class, () -> {
                userDetailsService.loadUserByUsername(username);
            });
        } catch (UsernameNotFoundException e) {
            assertTrue(e.getMessage().contains(username), "Exception message should contain the username");
        }
    }


    @Test
    void refreshToken() {
        String refreshToken = "validRefreshToken";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);
        when(jwtUtils.isValid(refreshToken, TokenType.REFRESH)).thenReturn(true);
        when(jwtUtils.getUsername(refreshToken, TokenType.REFRESH)).thenReturn("username");
        when(authUserRepository.findAuthIdByUsername("username")).thenReturn(1);
        TokenResponse tokenResponse = userDetailsService.refreshToken(refreshTokenRequest);

        assertNotNull(tokenResponse, "TokenResponse should not be null");
        assertNotNull(tokenResponse.getRefreshToken(), "Refresh token should not be null");
        }

    @Test
    void refreshTokenInvalidToken() {
        String refreshToken = "invalidRefreshToken";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);
        when(jwtUtils.isValid(refreshToken, TokenType.REFRESH)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userDetailsService.refreshToken(refreshTokenRequest));

        assertTrue(exception.getMessage().contains("Invalid refresh token"), "Exception message should indicate invalid refresh token");
    }
}
