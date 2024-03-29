package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.services.TrainerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainerRepositoryTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    @Test
    public void testFindByUserId() {
        int userId = 123;
        Trainer expectedTrainer = new Trainer();
        expectedTrainer.setUserId(User.builder()
                        .id(userId)
                .build());
        when(trainerRepository.findByIdOver(userId)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerService.getTrainer(userId);

        assertEquals(expectedTrainer, actualTrainer);
        verify(trainerRepository, times(1)).findByIdOver(userId);
    }

    @Test
    public void testFindAll() {
        List<Trainer> expectedTrainers = new ArrayList<>(); // Provide a list of Trainer instances that you expect to be returned

        when(trainerRepository.findAll()).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = trainerService.getAllTrainers();

        assertEquals(expectedTrainers, actualTrainers);
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllByUserIdActive() {
        boolean active = true;
        List<Trainer> expectedTrainers = new ArrayList<>(); // Provide a list of Trainer instances that you expect to be returned

        when(trainerRepository.findAllByUserId_Active(active)).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = trainerService.getTrainersNotAssignedAndActive();

        assertEquals(expectedTrainers, actualTrainers);
        verify(trainerRepository, times(1)).findAllByUserId_Active(active);
    }

    @Test
    public void testDeleteTrainee_TraineeDoesNotExist() {
        int traineeId = 1;
        when(trainerRepository.findById(traineeId)).thenReturn(null);
        assertThrows(NullPointerException.class,()->trainerService.deleteTrainee(traineeId));
        verify(trainerRepository, times(1)).findById(traineeId);
    }

}
