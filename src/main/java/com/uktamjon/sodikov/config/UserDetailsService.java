package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.enums.TokenType;
import com.uktamjon.sodikov.reponse.RefreshTokenRequest;
import com.uktamjon.sodikov.reponse.TokenRequest;
import com.uktamjon.sodikov.reponse.TokenResponse;
import com.uktamjon.sodikov.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private     final UserRepository authUserRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserDetailsService(UserRepository authUserRepository,
                              JwtUtils jwtUtils,
                              @Lazy AuthenticationManager authenticationManager) {
        this.authUserRepository = authUserRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User authUser = authUserRepository.findByUsername(username) ;
        return new UserDetails(authUser);
    }

    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(username);
    }

    public TokenResponse refreshToken(@NonNull RefreshTokenRequest tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();

        if (!jwtUtils.isValid(refreshToken, TokenType.REFRESH)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String username = jwtUtils.getUsername(refreshToken, TokenType.REFRESH);
        authUserRepository.findAuthIdByUsername(username);
        return TokenResponse.builder().refreshToken(refreshToken).refreshTokenExpiry(jwtUtils.getExpiry(refreshToken,TokenType.REFRESH)).build();

    }

}

