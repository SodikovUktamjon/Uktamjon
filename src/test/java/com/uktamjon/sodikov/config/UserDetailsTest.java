package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.domains.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
class UserDetailsTest {

    @Test
    void userDetailsConstructorShouldSetValuesCorrectly() {
        User authUser = new User();
        authUser.setId(1);
        authUser.setUsername("testUser");
        authUser.setPassword("password");
        authUser.setRole("USER");

        UserDetails userDetails = new UserDetails(authUser);

        assertEquals(authUser, userDetails.getAuthUser());
        assertEquals(authUser.getId(), userDetails.getId());
        assertEquals(authUser.getUsername(), userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));
        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getAuthoritiesShouldReturnSingleRole() {
        User authUser = new User();
        authUser.setRole("ADMIN");
        UserDetails userDetails = new UserDetails(authUser);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

}
