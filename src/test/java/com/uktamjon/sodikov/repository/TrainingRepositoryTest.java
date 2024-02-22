package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Training;
import com.uktamjon.sodikov.repository.TrainingRepository;
import com.uktamjon.sodikov.services.TrainingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingRepositoryTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingService trainingService;

    @Test
    public void testFindById() {
        int trainingId = 123;
        Training expectedTraining = new Training();

        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(expectedTraining));
        Training actualTraining = trainingService.getTraining(trainingId);

        assertEquals(expectedTraining, actualTraining);
        verify(trainingRepository, times(1)).findById(trainingId);
    }


}
