package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.*;
import com.uktamjon.sodikov.dtos.TrainerWorkload;
import com.uktamjon.sodikov.enums.ActionType;
import com.uktamjon.sodikov.repository.TraineeRepository;
import com.uktamjon.sodikov.repository.TrainerRepository;
import com.uktamjon.sodikov.repository.TrainingRepository;
import com.uktamjon.sodikov.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    public Training createTraining(Training training) {
        Trainer trainer = trainerRepository.findById(training.getTrainerId().getId()).orElse(null);
        TrainingType trainingType = trainingTypeRepository.findById(training.getTrainingType().getId()).orElse(null);


        List<Trainee> fetchedTrainees = new ArrayList<>();
        for (Trainee trainee : training.getTraineeId()) {
            Trainee fetchedTrainee = traineeRepository.findById(trainee.getId()).orElse(null);
            fetchedTrainees.add(fetchedTrainee);
        }

        if ((trainer == null || !trainer.getUserId().isActive()) && fetchedTrainees.isEmpty()) {
            log.error("Training not created: {}", training);
            return null;
        }
        training.setTrainerId(trainer);
        training.setTraineeId(fetchedTrainees);
        training.setTrainingType(trainingType);
        Training createdTraining = trainingRepository.save(training);
        log.info("Training created: {}", training);

        sendTrainerWorkloadMessage(createdTraining, ActionType.ADD);

        return createdTraining;

    }

    @Transactional
    public Training getTraining(int trainingId) {
        log.info("Training id: {}", trainingId);
        return trainingRepository.findById(trainingId).orElse(null);
    }

    @Transactional
    public List<Training> getAllTrainings() {
        log.info("Trainings listed");
        return trainingRepository.findAll();
    }

    @Transactional
    public void deleteTraining(int trainingId) {
        log.info("Training id: {}", trainingId);
        Optional<Training> training = trainingRepository.findById(trainingId);
        if (training.isPresent()) {
            sendTrainerWorkloadMessage(training.get(), ActionType.DELETE);

            trainingRepository.deleteById(trainingId);
        }


    }

    public int sendTrainerWorkloadMessage(Training training, ActionType actionType) {
        User user = training.getTrainerId().getUserId();
        TrainerWorkload trainerWorkload = TrainerWorkload.builder()
                .id(training.getTrainerId().getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .isActive(user.isActive())
                .startDate(training.getTrainingDate())
                .duration(training.getTrainingDuration())
                .actionType(actionType)
                .build();
        try {
            jmsTemplate.send("my-active-queue", session -> session.createObjectMessage(trainerWorkload));
        }catch (Exception ignored){

        }

        return trainerWorkload.getId();
    }

}
