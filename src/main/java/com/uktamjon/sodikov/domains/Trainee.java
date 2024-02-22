    package com.uktamjon.sodikov.domains;

    import com.fasterxml.jackson.annotation.JsonInclude;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.*;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    @Entity
    @Table(name = "trainee")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class Trainee {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private Date dateOfBirth;
        private String address;
        @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
        private User userId;

        @ManyToMany(mappedBy = "traineeId", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
        private List<Training> trainings = new ArrayList<>();
    }
