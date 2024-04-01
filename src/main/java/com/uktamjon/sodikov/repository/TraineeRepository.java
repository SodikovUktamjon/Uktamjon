package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Integer> {
    @Query("select t from Trainee t where t.userId.id=?1")
    Trainee findByUserId(int userId);
    @NotNull List<Trainee> findAll();
    @Query("select t from Trainee t where t.userId.username=?1")
    void deleteByUserId(String username);

}