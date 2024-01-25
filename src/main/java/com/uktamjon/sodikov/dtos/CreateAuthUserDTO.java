package com.uktamjon.sodikov.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

import java.io.Serializable;

/**
 * A DTO for the {@link com.uktamjon.sodikov.domains.User} entity
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateAuthUserDTO{
        String username;
        String password;
        String confirmPassword;

}
