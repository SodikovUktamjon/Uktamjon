package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.domains.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SessionUserTest {

    private SessionUser sessionUser;

    @BeforeEach
    void setUp() {
        sessionUser = new SessionUser();
    }

    @Test
    void user_ShouldReturnUserDetails_WhenPrincipalIsUserDetails() {
        UserDetails userDetails = new UserDetails(new User());
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDetails result = sessionUser.user();

        assertNotNull(result);
        assertEquals(userDetails, result);
    }

    @Test
    void user_ShouldReturnNull_WhenPrincipalIsNotUserDetails() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn("nonUserDetailsPrincipal");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserDetails result = sessionUser.user();

        assertNull(result);
    }

    @Test
    void id_ShouldReturnUserId_WhenUserDetailsIsNotNull() {
        UserDetails userDetails = new UserDetails(new User());
        setSecurityContextWithUserDetails(userDetails);

        Integer result = sessionUser.id();

        assertNotNull(result);
        assertEquals(userDetails.getId(), result);
    }

    @Test
    void id_ShouldReturnMinusOne_WhenUserDetailsIsNull() {
        setSecurityContextWithUserDetails(null);

        Integer result = sessionUser.id();

        assertEquals(-1, result);
    }

    @Test
    void username_ShouldReturnUsername_WhenUserDetailsIsNotNull() {
        UserDetails userDetails = new UserDetails(new User());
        setSecurityContextWithUserDetails(userDetails);

        String result = sessionUser.username();

        assertEquals(userDetails.getUsername(), result);
    }

    @Test
    void username_ShouldReturnEmptyString_WhenUserDetailsIsNull() {
        setSecurityContextWithUserDetails(null);

        String result = sessionUser.username();

        assertEquals("", result);
    }

    private void setSecurityContextWithUserDetails(UserDetails userDetails) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
