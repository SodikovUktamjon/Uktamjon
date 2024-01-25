package com.uktamjon.sodikov.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "training")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String trainingName;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Trainer trainerId;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Trainee> traineeId;
    @NotNull
    private LocalDateTime trainingDate;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private TrainingType trainingType;
    @NotNull
    private int trainingDuration;
}
