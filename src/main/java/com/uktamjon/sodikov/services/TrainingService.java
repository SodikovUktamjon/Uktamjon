package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.*;
import com.uktamjon.sodikov.dtos.ActionType;
import com.uktamjon.sodikov.dtos.TrainerWorkload;
import com.uktamjon.sodikov.repository.TraineeRepository;
import com.uktamjon.sodikov.repository.TrainerRepository;
import com.uktamjon.sodikov.repository.TrainingRepository;
import com.uktamjon.sodikov.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {

    private final TrainerWorkloadFeignClient trainerWorkloadFeignClient;
    private final TrainingRepository trainingRepository;
    private final TrainerRepository trainerRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    public Training createTraining(Training training) {
        Trainer trainer = trainerRepository.findById(training.getTrainer().getId()).orElse(null);
        TrainingType trainingType = trainingTypeRepository.findById(training.getTrainingType().getId()).orElse(null);

        // find Trainees in the incoming Training object
        List<Trainee> fetchedTrainees = new ArrayList<>();
        for (Trainee trainee : training.getTrainees()) {
            Trainee fetchedTrainee = traineeRepository.findById(trainee.getId()).orElse(null);
            fetchedTrainees.add(fetchedTrainee);
        }

        if ((trainer == null || !trainer.getUser().isActive()) && fetchedTrainees.isEmpty()) {
            log.error("Training not created: {}", training);
            return null;
        }
        training.setTrainer(trainer);
        training.setTrainees(fetchedTrainees);
        training.setTrainingType(trainingType);
        Training createdTraining = trainingRepository.save(training);
        log.info("Training created: {}", training);
        User user = createdTraining.getTrainer().getUser();
        trainerWorkloadFeignClient.modifyWorkload(
                TrainerWorkload.builder()
                        .id(createdTraining.getTrainer().getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                        .isActive(user.isActive())
                        .startDate(createdTraining.getTrainingDate())
                        .duration(createdTraining.getTrainingDuration())
                        .actionType(ActionType.ADD)
                        .build()
        );
        return createdTraining;
    }

    public void deleteTraining(int trainingId) {
        log.info("Training id: {}", trainingId);
        Optional<Training> training = trainingRepository.findById(trainingId);
        if (training.isPresent()) {
            User user = training.get().getTrainer().getUser();
            trainerWorkloadFeignClient.modifyWorkload(
                    TrainerWorkload.builder()
                            .id(training.get().getTrainer().getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .username(user.getUsername())
                            .isActive(user.isActive())
                            .startDate(training.get().getTrainingDate())
                            .duration(training.get().getTrainingDuration())
                            .actionType(ActionType.DELETE)
                            .build()
            );
            trainingRepository.deleteById(trainingId);
        }
    }

    public Training getTraining(int trainingId) {
        log.info("Training id: {}", trainingId);
        return trainingRepository.findById(trainingId).orElse(null);
    }




}
