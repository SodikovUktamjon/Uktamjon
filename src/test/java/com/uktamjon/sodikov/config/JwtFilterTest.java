package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.enums.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class JwtFilterTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JwtUtils jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;





    @Test
    void doFilterInternal_ValidToken_ShouldSetAuthentication() throws ServletException, IOException {
        String token = "validToken";
        String username = "testUser";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        UserDetails userDetails = new User(username, "", Collections.emptyList());

        when(jwtTokenUtil.isValid(token, TokenType.ACCESS)).thenReturn(true);
        when(jwtTokenUtil.getUsername(token, TokenType.ACCESS)).thenReturn(username);

        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService).loadUserByUsername(username);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_InvalidToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        String invalidToken = "invalidToken";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + invalidToken);

        when(jwtTokenUtil.isValid(invalidToken, TokenType.ACCESS)).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(userDetailsService);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_MissingAuthorizationHeader_ShouldNotSetAuthentication() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);


        jwtFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(userDetailsService, jwtTokenUtil);
        verify(filterChain).doFilter(request, response);
    }
}
