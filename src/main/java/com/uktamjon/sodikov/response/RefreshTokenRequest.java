package com.uktamjon.sodikov.response;

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
