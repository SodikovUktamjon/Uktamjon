package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.*;
import com.uktamjon.sodikov.dtos.TrainerWorkload;
import com.uktamjon.sodikov.repository.TraineeRepository;
import com.uktamjon.sodikov.repository.TrainerRepository;
import com.uktamjon.sodikov.repository.TrainingRepository;
import com.uktamjon.sodikov.repository.TrainingTypeRepository;
import com.uktamjon.sodikov.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;


    @Mock
    private TraineeRepository traineeRepository;
    @Mock
    private TrainerRepository trainerRepository;
    @Mock
    private TrainingTypeRepository trainingTypeRepository;



    @InjectMocks
    private TrainingService trainingService;
    @Mock
    private TrainerWorkloadFeignClient workloadFeignClient;
    @BeforeEach
    void setUp() {
        trainingRepository = mock(TrainingRepository.class);
        workloadFeignClient = mock(TrainerWorkloadFeignClient.class);
        trainerRepository = mock(TrainerRepository.class);
        trainingTypeRepository = mock(TrainingTypeRepository.class);
        traineeRepository = mock(TraineeRepository.class);
        trainingService = new TrainingService(trainingRepository,workloadFeignClient,traineeRepository,trainerRepository,trainingTypeRepository);
    }


    @Test
    public void testCreateTraining_Successful() {
        Training training = new Training();
        Trainer trainer = new Trainer();
        List<Trainee> trainees = new ArrayList<>();

        when(trainerRepository.findById(any())).thenReturn(Optional.of(trainer));
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.of(new TrainingType()));
        when(traineeRepository.findById(any())).thenReturn(Optional.of(new Trainee()));
        when(trainingRepository.save(any())).thenReturn(training);

        doNothing().when(workloadFeignClient).modifyWorkload(any());

        Training createdTraining = trainingService.createTraining(training);

        assertNotNull(createdTraining);
    }

    @Test
    public void testCreateTraining_Unsuccessful() {
        Training training = new Training();

        when(trainerRepository.findById(any())).thenReturn(Optional.empty());
        Training createdTraining = trainingService.createTraining(training);

        assertNull(createdTraining);
    }

    @Test
    public void testGetTraining() {
        int trainingId = 1;
        Training training = new Training();

        when(trainingRepository.findById(any())).thenReturn(Optional.of(training));

        Training retrievedTraining = trainingService.getTraining(trainingId);

        assertNotNull(retrievedTraining);
    }

    @Test
    public void testDeleteTraining_TrainingExists() {
        int trainingId = 1;
        Training training = new Training();
        training.setId(trainingId);
        User user = User.builder().id(1)
                .isActive(true)
                .build();
        training.setTrainerId(Trainer.builder().userId(user).build());

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));

        trainingService.deleteTraining(trainingId);

        verify(trainingRepository, times(1)).findById(trainingId);
        verify(trainingRepository, times(1)).deleteById(trainingId);
        verify(workloadFeignClient, times(1)).modifyWorkload(any(TrainerWorkload.class));
    }

    @Test
    public void testDeleteTraining_TrainingDoesNotExist() {
        int trainingId = 1;

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        trainingService.deleteTraining(trainingId);

        verify(trainingRepository, times(0)).delete(any(Training.class));
        verify(workloadFeignClient, times(0)).modifyWorkload(any(TrainerWorkload.class));
    }
}
