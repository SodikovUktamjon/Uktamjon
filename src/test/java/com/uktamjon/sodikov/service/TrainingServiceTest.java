package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.*;
import com.uktamjon.sodikov.repository.TrainingRepository;
import com.uktamjon.sodikov.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainingTypeService trainingTypeService;

    @InjectMocks
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        trainingRepository = mock(TrainingRepository.class);
        trainerService = mock(TrainerService.class);
        traineeService = mock(TraineeService.class);
        trainingTypeService = mock(TrainingTypeService.class);
        trainingService = new TrainingService(trainingRepository, trainerService, traineeService, trainingTypeService);
    }


    @Test
    public void testCreateTraining_AllServicesReturnNull() {
        Training training = new Training();
        Trainer trainer = Trainer.builder().id(1).build();
        List<Trainee> trainees = new ArrayList<>(
                Collections.singletonList(
                        Trainee.builder()
                                .id(1)
                                .build()));
        TrainingType trainingType = TrainingType.builder().id(1).build();
        User user = User.builder().id(1).build();
        User user1 = User.builder().id(2).build();
        trainees.get(0).setUserId(user);
        trainer.setUserId(user1);


        training.setTraineeId(trainees);
        training.setTrainerId(trainer);
        training.setTrainingType(trainingType);

        when(trainingTypeService.getTrainingTypeById(training.getTrainingType().getId())).thenReturn(null);
        when(trainerService.getTrainer(training.getTrainerId().getId())).thenReturn(null);
        when(traineeService.getTrainee(training.getTraineeId().get(0).getId())).thenReturn(null);
        Training createdTraining = trainingService.createTraining(training);
        assertNull(createdTraining);
    }

    @Test
    public void testCreateTraining_AllServicesReturnValidData() {
        Training training = new Training();
        Trainer trainer = Trainer.builder().id(1).build();
        List<Trainee> trainees = new ArrayList<>(
                Collections.singletonList(
                        Trainee.builder()
                                .id(1)
                                .build()));
        TrainingType trainingType = TrainingType.builder().id(1).build();
        User user = User.builder().id(1).build();
        User user1 = User.builder().id(2).build();
        trainees.get(0).setUserId(user);
        trainer.setUserId(user1);


        training.setTraineeId(trainees);
        training.setTrainerId(trainer);
        training.setTrainingType(trainingType);

        when(trainingTypeService.getTrainingTypeById(training.getTrainingType().getId())).thenReturn(null);

        when(trainerService.getTrainer(training.getTrainerId().getId())).thenReturn(new Trainer());
        when(traineeService.getTrainee(training.getTrainerId().getId())).thenReturn(new Trainee());
        when(trainingRepository.save(training)).thenReturn(training);
        Training createdTraining = trainingService.createTraining(training);
        assertEquals(training, createdTraining);
    }

    @Test
    public void testGetTraining_TrainingExists() {
        int trainingId = 1;
        Training expectedTraining = new Training();
        expectedTraining.setId(trainingId);
        when(trainingRepository.findById(trainingId)).thenReturn(expectedTraining);
        Training retrievedTraining = trainingService.getTraining(trainingId);
        assertEquals(expectedTraining, retrievedTraining);
    }

    @Test
    public void testGetTraining_TrainingDoesNotExist() {
        int trainingId = 1;
        when(trainingRepository.findById(trainingId)).thenReturn(null);
        Training retrievedTraining = trainingService.getTraining(trainingId);
        assertNull(retrievedTraining);
    }
}
