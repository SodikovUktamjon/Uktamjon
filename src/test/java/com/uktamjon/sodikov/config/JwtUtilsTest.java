package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.enums.TokenType;
import com.uktamjon.sodikov.response.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Claims claims;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtUtils, "ACCESS_TOKEN_SECRET_KEY", "357638792F423F4528482B4D6250655368566D597133743677397A24432646294A404E635266546A576E5A7234753778214125442A472D4B6150645367566B58");
        ReflectionTestUtils.setField(jwtUtils, "REFRESH_TOKEN_SECRET_KEY", "7134743777217A25432A462D4A614E645267556B58703272357538782F413F44");
        ReflectionTestUtils.setField(jwtUtils, "accessTokenExpiry", 3600000L);
        ReflectionTestUtils.setField(jwtUtils, "refreshTokenExpiry", 86400000L);
    }

    @Test
    void generateToken_shouldGenerateValidTokens() {
        TokenResponse tokenResponse = jwtUtils.generateToken("testUser");

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
    }

    @Test
    void generateAccessToken_shouldGenerateValidToken() {
        TokenResponse tokenResponse = new TokenResponse();
        jwtUtils.generateAccessToken("testUser", tokenResponse);

        assertNotNull(tokenResponse.getAccessToken());
    }

    @Test
    void generateRefreshToken_shouldGenerateValidToken() {
        TokenResponse tokenResponse = new TokenResponse();
        jwtUtils.generateRefreshToken("testUser", tokenResponse);

        assertNotNull(tokenResponse.getRefreshToken());
    }

    @Test
    void isValid_shouldReturnTrueForValidToken() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setIssuer("sheengo.live")
                .setExpiration(new Date(System.currentTimeMillis() + 3600000L))
                .signWith(jwtUtils.signKey(TokenType.ACCESS))
                .compact();

        assertTrue(jwtUtils.isValid(token, TokenType.ACCESS));
    }

    @Test
    void isValid_shouldReturnFalseForExpiredToken() {
        String token = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date())
                .setIssuer("sheengo.live")
                .setExpiration(new Date(System.currentTimeMillis() - 1000L))
                .signWith(jwtUtils.signKey(TokenType.ACCESS))
                .compact();

        assertFalse(jwtUtils.isValid(token, TokenType.ACCESS));
    }


}
