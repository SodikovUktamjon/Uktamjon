package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Trainee;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    Trainee findByUserId(int userId);
    @NotNull List<Trainee> findAll();

}