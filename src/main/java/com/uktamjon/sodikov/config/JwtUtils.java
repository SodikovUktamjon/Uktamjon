package com.uktamjon.sodikov.config;


import com.uktamjon.sodikov.enums.TokenType;
import com.uktamjon.sodikov.response.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


import static com.uktamjon.sodikov.enums.TokenType.ACCESS;
import static com.uktamjon.sodikov.enums.TokenType.REFRESH;

@Component
public class JwtUtils {


    @Value("${jwt.access.token.expiry}")
    private long accessTokenExpiry;

    @Value("${jwt.access.token.secret.key}")
    public String ACCESS_TOKEN_SECRET_KEY;


    @Value("${jwt.refresh.token.expiry}")
    private long refreshTokenExpiry;

    @Value("${jwt.refresh.token.secret.key}")
    public String REFRESH_TOKEN_SECRET_KEY;


    public TokenResponse generateToken(@NonNull String username) {
        TokenResponse tokenResponse = new TokenResponse();
        generateAccessToken(username, tokenResponse);
        generateRefreshToken(username, tokenResponse);
        return tokenResponse;
    }


    public void generateAccessToken(@NotNull String username, @NotNull TokenResponse tokenResponse) {
        tokenResponse.setAccessTokenExpiry(new Date(System.currentTimeMillis() + accessTokenExpiry));
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("sheengo.live")
                .setExpiration(tokenResponse.getAccessTokenExpiry())
                .signWith(signKey(ACCESS), SignatureAlgorithm.HS512)
                .compact();
        tokenResponse.setAccessToken(accessToken);
    }
    public void generateRefreshToken(@NotNull String username, @NotNull TokenResponse tokenResponse) {
        tokenResponse.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + refreshTokenExpiry));
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setIssuer("sheengo.live")
                .setExpiration(tokenResponse.getRefreshTokenExpiry())
                .signWith(signKey(REFRESH), SignatureAlgorithm.HS256)
                .compact();
        tokenResponse.setRefreshToken(refreshToken);
    }


    public boolean isValid(@NonNull String token, TokenType tokenType) {
        try {
            Claims claims = getClaims(token, tokenType);
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsername(@NonNull String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getSubject();
    }

    public Claims getClaims(String token, TokenType tokenType) {
        return Jwts.parserBuilder()
                .setSigningKey(signKey(tokenType))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key signKey(TokenType tokenType) {
        byte[] bytes = Decoders.BASE64.decode(tokenType.equals(ACCESS) ? ACCESS_TOKEN_SECRET_KEY : REFRESH_TOKEN_SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Date getExpiry(String token, TokenType tokenType) {
        Claims claims = getClaims(token, tokenType);
        return claims.getExpiration();
    }
}