package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Integer> {

}
