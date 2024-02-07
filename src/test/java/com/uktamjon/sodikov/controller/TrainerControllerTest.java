package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TrainerService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @Mock
    private CustomMetricsService customMetricsService;

    @InjectMocks
    private TrainerController trainerController;

    @Test
    void testCreateTrainer() {
        Trainer trainer = new Trainer();
        CreateResponse createResponse = new CreateResponse();
        when(trainerService.createTrainer(trainer)).thenReturn(createResponse);

        ResponseEntity<CreateResponse> responseEntity = trainerController.createTrainer(trainer);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).createTrainer(trainer);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
    @Test
    void testCreateTrainerBadRequest() {
        Trainer trainer = new Trainer();
        when(trainerService.createTrainer(trainer)).thenReturn(null);

        ResponseEntity<CreateResponse> responseEntity = trainerController.createTrainer(trainer);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).createTrainer(trainer);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTrainer() {
        Trainer trainer = new Trainer();
        when(trainerService.updateTrainer(trainer)).thenReturn(true);

        ResponseEntity<String> responseEntity = trainerController.updateTrainer(trainer);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).updateTrainer(trainer);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    @Test
    void testUpdateTrainerBadRequest() {
        Trainer trainer = new Trainer();
        when(trainerService.updateTrainer(trainer)).thenReturn(false);

        ResponseEntity<String> responseEntity = trainerController.updateTrainer(trainer);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).updateTrainer(trainer);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testGetTrainer() {
        int trainerId = 1;
        Trainer trainer = new Trainer();
        when(trainerService.getTrainer(trainerId)).thenReturn(trainer);

        ResponseEntity<Trainer> responseEntity = trainerController.getTrainer(trainerId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).getTrainer(trainerId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTrainerNotFound() {
        int trainerId = 1;
        when(trainerService.getTrainer(trainerId)).thenReturn(null);

        ResponseEntity<Trainer> responseEntity = trainerController.getTrainer(trainerId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).getTrainer(trainerId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    void testGetTrainerByUsername() {
        String username = "testUser";
        Trainer trainer = new Trainer();
        when(trainerService.getTrainer(username)).thenReturn(trainer);

        ResponseEntity<Trainer> responseEntity = trainerController.getTrainerByUsername(username);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).getTrainer(username);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTrainerByUsernameNotFound() {
        String username = "testUser";
        when(trainerService.getTrainer(username)).thenReturn(null);

        ResponseEntity<Trainer> responseEntity = trainerController.getTrainerByUsername(username);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).getTrainer(username);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    void testGetAllTrainers() {
        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        when(trainerService.getAllTrainers()).thenReturn(trainers);

        ResponseEntity<List<Trainer>> responseEntity = trainerController.getAllTrainers();

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).getAllTrainers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testActivateAndDeactivateTrainer() {
        int trainerId = 1;

        ResponseEntity<String> responseEntity = trainerController.activateandDeactivateTrainer(trainerId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).activateAndDeactivate(trainerId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllActiveAndNotAssignedTrainers() {
        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        when(trainerService.getTrainersNotAssignedAndActive()).thenReturn(trainers);

        ResponseEntity<List<Trainer>> responseEntity = trainerController.getAllActiveAndNotAssignedTrainers();

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainerService).getTrainersNotAssignedAndActive();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
