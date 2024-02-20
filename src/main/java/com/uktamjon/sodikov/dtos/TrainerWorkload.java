package com.uktamjon.sodikov.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerWorkload {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private int duration;
    private LocalDateTime startDate;
    private ActionType actionType;
}
