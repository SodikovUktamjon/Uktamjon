package com.uktamjon.sodikov.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TrainerWorkload {

    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private int duration;
    private LocalDateTime startDate;
}
