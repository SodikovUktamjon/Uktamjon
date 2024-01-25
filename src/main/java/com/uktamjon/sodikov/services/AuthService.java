package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.config.JwtUtils;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateAuthUserDTO;
import com.uktamjon.sodikov.dtos.GetTokenDTO;
import com.uktamjon.sodikov.reponse.TokenResponse;
import com.uktamjon.sodikov.repository.UserRepository;
import com.uktamjon.sodikov.utils.BruteForceProtectionService;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final PasswordGeneratorService passwordGeneratorService;
    private final UserRepository authUserRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final BruteForceProtectionService bruteForceProtectionService;

   @CircuitBreaker(name = "authService", fallbackMethod = "loginFallback")
    public GetTokenDTO login(CreateAuthUserDTO dto) {
        if(bruteForceProtectionService.isUserBlocked(dto.getUsername())) {
            return null;
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        User user = authUserRepository.findByUsername(dto.getUsername());
        if(passwordGeneratorService.checkPassword(passwordGeneratorService.encryptPassword(dto.getPassword()), user.getPassword())) {
            TokenResponse tokenResponse = jwtUtils.generateToken(user.getUsername());
            return GetTokenDTO.builder()
                    .token(tokenResponse.getAccessToken())
                    .build();
        }
        bruteForceProtectionService.recordFailedLogin(dto.getUsername());
        return null;

    }
    public GetTokenDTO loginFallback(CreateAuthUserDTO dto, Exception e) {
        bruteForceProtectionService.recordFailedLogin(dto.getUsername());
        log.trace("Exception while logging in: {}", e.getMessage());
        return GetTokenDTO.builder()
                .token("fallback-token")
                .build();
    }


}