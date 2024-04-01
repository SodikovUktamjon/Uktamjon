package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.TrainingType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingTypeRepository extends JpaRepository<TrainingType, Integer> {
    @Query("select (count(t) > 0) from TrainingType t where t.trainingTypeName = ?1")
    boolean existsByTrainingTypeName(String name);

    TrainingType findByTrainingTypeName(String name);

    @Transactional
    @Modifying
    void deleteByTrainingTypeName(String trainingName);


}