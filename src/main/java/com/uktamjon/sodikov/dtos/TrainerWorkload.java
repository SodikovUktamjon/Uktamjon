package com.uktamjon.sodikov.dtos;

import com.uktamjon.sodikov.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerWorkload implements Serializable {
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isActive;
    private int duration;
    private LocalDateTime startDate;
    private ActionType actionType;
}
