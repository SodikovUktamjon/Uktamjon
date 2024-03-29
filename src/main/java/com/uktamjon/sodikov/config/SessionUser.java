package com.uktamjon.sodikov.config;

import com.uktamjon.sodikov.config.UserDetails;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class SessionUser {

    public UserDetails user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails ud)
            return ud;
        return null;
    }


    public Integer id() {
        UserDetails user = user();

        if (Objects.isNull(user))
            return -1;
        return user.getId();
    }

    public String username() {
        UserDetails user = user();
        if (Objects.isNull(user))
            return "";
        return user.getUsername();
    }
}