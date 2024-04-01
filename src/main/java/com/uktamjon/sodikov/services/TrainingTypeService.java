package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;


    public TrainingType createTrainingType(TrainingType trainingType) {
        log.info("Training type name: {}", trainingType.getTrainingTypeName());
        return trainingTypeRepository.save(trainingType);
    }

    public TrainingType updateTrainingType(TrainingType trainingType) {
        if (trainingTypeRepository.existsByTrainingTypeName(trainingType.getTrainingTypeName())) {
            log.error("Training type not updated: {}", trainingType);
            return null;
        }
        log.info("Training type name: {}", trainingType.getTrainingTypeName());
        return trainingTypeRepository.save(trainingType);
    }

    public void deleteTrainingType(int trainingTypeId) {
        trainingTypeRepository.deleteById(trainingTypeId);
        log.info("Training type id: {}", trainingTypeId);
    }
    @Transactional
    public void deleteTrainingType(String trainingTypeId) {
        trainingTypeRepository.deleteByTrainingTypeName(trainingTypeId);
        log.info("Training type id: {}", trainingTypeId);
    }

    public TrainingType getTrainingTypeById(int trainingTypeId) {
        log.info("Training type id: {}", trainingTypeId);
        return trainingTypeRepository.findById(trainingTypeId).orElse(null);
    }

    public List<TrainingType> getAllTrainingTypes() {
        log.info("Getting all training types");
        return trainingTypeRepository.findAll();
    }
    public TrainingType getTrainingTypeByTrainingName(String trainingTypeId) {
        log.info("Training type id: {}", trainingTypeId);
        return trainingTypeRepository.findByTrainingTypeName(trainingTypeId);
    }


}
