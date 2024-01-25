package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.Training;
import com.uktamjon.sodikov.services.TrainingService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingControllerTest {

    @Mock
    private TrainingService trainingService;

    @Mock
    private CustomMetricsService customMetricsService;

    @InjectMocks
    private TrainingController trainingController;

    @Test
    void testCreateTraining() {
        Training training = new Training();
        Training createdTraining = new Training();
        when(trainingService.createTraining(training)).thenReturn(createdTraining);

        ResponseEntity<Training> responseEntity = trainingController.createTraining(training);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingService).createTraining(training);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testCreateTrainingFailure() {
        Training training = new Training();
        when(trainingService.createTraining(training)).thenReturn(null);

        ResponseEntity<Training> responseEntity = trainingController.createTraining(training);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingService).createTraining(training);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testGetTraining() {
        int trainingId = 1;
        Training training = new Training();
        when(trainingService.getTraining(trainingId)).thenReturn(training);

        ResponseEntity<Training> responseEntity = trainingController.getTraining(trainingId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingService).getTraining(trainingId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTrainingNotFound() {
        int trainingId = 1;
        when(trainingService.getTraining(trainingId)).thenReturn(null);

        ResponseEntity<Training> responseEntity = trainingController.getTraining(trainingId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingService).getTraining(trainingId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
