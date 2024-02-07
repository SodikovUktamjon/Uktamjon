package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.repository.TrainerRepository;
import com.uktamjon.sodikov.services.TrainerService;
import com.uktamjon.sodikov.services.TrainingTypeService;
import com.uktamjon.sodikov.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrainerService trainerService;

    @BeforeEach
    void setUp() {

        trainerRepository = mock(TrainerRepository.class);
        userService = mock(UserService.class);
        trainingTypeService = mock(TrainingTypeService.class);
        trainerService = new TrainerService(trainerRepository,trainingTypeService, userService);
    }


    @Test
    public void testCreateTrainer_ExistingTrainingTypeAndUser() {
        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUserId(user);

        when(trainingTypeService.getTrainingTypeById(trainer.getSpecialization())).thenReturn(new TrainingType());

        when(userService.getUserById(trainer.getUserId().getId())).thenReturn(new User());

        CreateResponse createdTrainer = trainerService.createTrainer(trainer);
        assertNull(createdTrainer);
    }

    @Test
    public void testCreateTrainer_NonExistingTrainingTypeAndUser() {
        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUserId(user);
        when(trainingTypeService.getTrainingTypeById(trainer.getSpecialization())).thenReturn(null);
        when(userService.getUserById(trainer.getUserId().getId())).thenReturn(null);
        when(userService.createUser(user)).thenReturn(user);
        when(trainerRepository.save(trainer)).thenReturn(trainer);
        CreateResponse createdTrainer = trainerService.createTrainer(trainer);
        assertEquals(trainer.getUserId().getUsername(), createdTrainer.getUsername());
        assertEquals(trainer.getUserId().getPassword(), createdTrainer.getPassword());
    }
    @Test
    public void testUpdateTrainer_ExistingTrainingTypeAndTrainer() {
        Trainer trainer = new Trainer();


        when(trainingTypeService.getTrainingTypeById(trainer.getSpecialization())).thenReturn(new TrainingType());

        when(trainerRepository.existsById(trainer.getId())).thenReturn(true);

        boolean updated = trainerService.updateTrainer(trainer);

        assertFalse(updated);

        verifyNoInteractions(userService);
        verifyNoInteractions(trainerRepository);
    }

    @Test
    public void testUpdateTrainer_NonExistingTrainingTypeAndExistingTrainer() {
        Trainer trainer = new Trainer();
        User user = new User();
        trainer.setUserId(user);

        when(trainingTypeService.getTrainingTypeById(trainer.getSpecialization())).thenReturn(null);

        when(trainerRepository.existsById(trainer.getId())).thenReturn(true);

        doNothing().when(userService).updateUser(user);

        when(trainerRepository.save(trainer)).thenReturn(trainer);

        boolean updated = trainerService.updateTrainer(trainer);

            assertTrue(updated);

        verify(userService, times(1)).updateUser(user);
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    public void testGetTrainer_TrainerExists() {
        int trainerId = 1;
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setId(trainerId);
        when(trainerRepository.findByUserId(trainerId)).thenReturn(expectedTrainer);
        Trainer retrievedTrainer = trainerService.getTrainer(trainerId);
        assertEquals(expectedTrainer, retrievedTrainer);

    }

    @Test
    public void testGetTrainer_TrainerDoesNotExist() {
        int trainerId = 1;
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());
        Trainer retrievedTrainer = trainerService.getTrainer(trainerId);
        assertNull(retrievedTrainer);
    }
}
