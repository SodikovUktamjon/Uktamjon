package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.config.JwtUtils;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateAuthUserDTO;
import com.uktamjon.sodikov.dtos.GetTokenDTO;
import com.uktamjon.sodikov.repository.UserRepository;
import com.uktamjon.sodikov.response.TokenResponse;
import com.uktamjon.sodikov.services.AuthService;
import com.uktamjon.sodikov.utils.BruteForceProtectionService;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordGeneratorService passwordGeneratorService;

    @Mock
    private UserRepository authUserRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BruteForceProtectionService bruteForceProtectionService;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        AuthService authService = new AuthService(passwordGeneratorService, authUserRepository, jwtUtils, authenticationManager, bruteForceProtectionService);
    }

    @Test
    void login_Success() {
        CreateAuthUserDTO dto = CreateAuthUserDTO.builder()
                .username("username")
                .password("password")
                .build();

        when(bruteForceProtectionService.isUserBlocked(dto.getUsername())).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(authUserRepository.findByUsername(Mockito.any())).thenReturn(User.builder()
                .password(dto.getPassword())
                .username(dto.getUsername())
                .build());

        when(passwordGeneratorService.checkPassword(any(), any())).thenReturn(true);
        when(jwtUtils.generateToken(any())).thenReturn(TokenResponse.builder().accessToken("access-token").build());

        GetTokenDTO result = authService.login(dto);

        assertNotNull(result);
        assertEquals("access-token", result.getToken());
        verify(bruteForceProtectionService, never()).recordFailedLogin(any());
    }

    @Test
    void login_Failure() {
        CreateAuthUserDTO dto = CreateAuthUserDTO.builder()
                .username("username")
                .password("password")
                .build();

        when(bruteForceProtectionService.isUserBlocked(dto.getUsername())).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(authUserRepository.findByUsername(dto.getUsername())).thenReturn(User.builder().build());
        when(passwordGeneratorService.checkPassword(any(), any())).thenReturn(false);
        GetTokenDTO result = authService.login(dto);

        assertNull(result);
        verify(bruteForceProtectionService, times(1)).recordFailedLogin(dto.getUsername());
    }

    @Test
    void login_UserBlocked() {
        CreateAuthUserDTO dto = CreateAuthUserDTO.builder()
                .username("blockedUser")
                .password("password")
                .build();

        when(bruteForceProtectionService.isUserBlocked(dto.getUsername())).thenReturn(true);

        GetTokenDTO result = authService.login(dto);

        assertNull(result);
        verify(bruteForceProtectionService, never()).recordFailedLogin(any());
    }
}
