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
import static org.mockito.Mockito.*;

public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingTypeService trainingTypeService;
    @Mock
    private TraineeService traineeService;



    @InjectMocks
    private TrainingService trainingService;
    @Mock
    private TrainerWorkloadFeignClient workloadFeignClient;
    @BeforeEach
    void setUp() {
        trainingRepository = mock(TrainingRepository.class);
        trainerService = mock(TrainerService.class);
        workloadFeignClient = mock(TrainerWorkloadFeignClient.class);
        trainingService = new TrainingService(trainingRepository,workloadFeignClient,trainerService);
    }


    @Test
    public void testCreateTraining_AllServicesReturnNull() {
        Training training = new Training();
        Trainer trainer = Trainer.builder().build();
        training.setTrainerId(trainer);
        when(trainerService.getTrainer(training.getTrainerId().getId())).thenReturn(null);
        Training createdTraining = trainingService.createTraining(training);
        assertNull(createdTraining);
    }

    @Test
    public void testCreateTraining_UserNotActive() {
        Training training = new Training();
        Trainer trainer = Trainer.builder()
                .userId(User.builder()
                        .isActive(false)
                        .build())
                .build();
        training.setTrainerId(trainer);

        when(trainerService.getTrainer(training.getTrainerId().getId())).thenReturn(trainer);
        Training createdTraining = trainingService.createTraining(training);
        assertNull(createdTraining);
    }

    @Test
    public void testCreateTraining_TraineesAreNotNull() {
        Training training = new Training();
        Trainer trainer = Trainer.builder()
                .userId(User.builder()
                        .isActive(false)
                        .build())
                .build();
        training.setTrainerId(trainer);



        List<Trainee> trainees = new ArrayList<>(
                Collections.singletonList(
                        Trainee.builder()
                                .build()
                )

        );
        training.setTraineeId(trainees);

        when(trainerService.getTrainer(training.getTrainerId().getId())).thenReturn(trainer);

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
        User user = User.builder().id(1)
                .isActive(true)
                .build();
        User user1 = User.builder()
                .id(2)
                .isActive(true)
                .build();
        trainees.get(0).setUserId(user);
        trainer.setUserId(user1);



        training.setTraineeId(trainees);
        training.setTrainerId(trainer);
        training.setTrainingType(trainingType);


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
