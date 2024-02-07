package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.config.UserDetailsService;
import com.uktamjon.sodikov.dtos.CreateAuthUserDTO;
import com.uktamjon.sodikov.dtos.GetTokenDTO;
import com.uktamjon.sodikov.response.RefreshTokenRequest;
import com.uktamjon.sodikov.response.TokenRequest;
import com.uktamjon.sodikov.response.TokenResponse;
import com.uktamjon.sodikov.services.AuthService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthService authService;
    private final CustomMetricsService customMetricsService;


    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@Valid TokenRequest tokenRequest) {
        customMetricsService.recordCustomMetric(1);
        return ResponseEntity.ok(userDetailsService.generateToken(tokenRequest));
    }


    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid RefreshTokenRequest refreshTokenRequest) {
        customMetricsService.recordCustomMetric(1);
        return ResponseEntity.ok(userDetailsService.refreshToken(refreshTokenRequest));
    }


    @GetMapping("/login")
    public ResponseEntity<GetTokenDTO> login(CreateAuthUserDTO request) {
        customMetricsService.recordCustomMetric(1);
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        customMetricsService.recordCustomMetric(1);
        return ResponseEntity.noContent().build();
    }

}