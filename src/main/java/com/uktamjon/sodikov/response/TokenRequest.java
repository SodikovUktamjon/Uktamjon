package com.uktamjon.sodikov.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenRequest {

    @NotBlank String username;
    @NotBlank String password;
}