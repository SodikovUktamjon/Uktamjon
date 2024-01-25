package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.Training;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.TrainerWorkload;
import com.uktamjon.sodikov.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainerService trainerService;
    private final TraineeService traineeService;
    private final TrainingTypeService trainingTypeService;
    private final TrainerWorkloadFeignClient trainerWorkloadFeignClient;

    public Training createTraining(Training training) {
        if (trainerService.getTrainer(training.getTrainerId().getId())==null
                && !training.getTrainerId().getUserId().isActive() &&training.getTraineeId()!=null) {
            log.error("Training not created: {}", training);

            return null;
        }
        log.info("Training created: {}", training);
        User userId = training.getTrainerId().getUserId();
        trainerWorkloadFeignClient.createTrainer(
                TrainerWorkload.builder()
                        .firstName(userId.getFirstName())
                        .lastName(userId.getLastName())
                        .username(userId.getUsername())
                        .isActive(userId.isActive())
                        .startDate(training.getTrainingDate())
                        .duration(training.getTrainingDuration())
                        .build()
        );
        return trainingRepository.save(training);
    }

    public Training getTraining(int trainingId) {
        log.info("Training id: {}", trainingId);
        return trainingRepository.findById(trainingId);
    }




}
