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
    private Trainer trainer;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "training_trainee",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private List<Trainee> trainees;
    @NotNull
    private LocalDateTime trainingDate;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private TrainingType trainingType;
    @NotNull
    private int trainingDuration;
}
