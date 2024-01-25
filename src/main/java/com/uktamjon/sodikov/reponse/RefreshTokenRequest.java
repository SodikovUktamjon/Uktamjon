package com.uktamjon.sodikov.reponse;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class  RefreshTokenRequest {
    @NotBlank String refreshToken;
}
