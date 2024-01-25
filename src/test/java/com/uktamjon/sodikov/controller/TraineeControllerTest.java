package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TraineeService;
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
class TraineeControllerTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private CustomMetricsService customMetricsService;

    @InjectMocks
    private TraineeController traineeController;

    @Test
    void testCreateTrainee() {


        Trainee trainee = new Trainee();
        CreateResponse createResponse = new CreateResponse();
        when(traineeService.createTrainee(trainee)).thenReturn(createResponse);



        ResponseEntity<CreateResponse> responseEntity = traineeController.createTrainee(trainee);



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).createTrainee(trainee);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateTrainee() {


        Trainee trainee = new Trainee();
        when(traineeService.updateTrainee(trainee)).thenReturn(trainee);



        ResponseEntity<Trainee> responseEntity = traineeController.updateTrainee(trainee);



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).updateTrainee(trainee);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testDeleteTrainee() {


        int traineeId = 1;



        ResponseEntity<String> responseEntity = traineeController.deleteTrainee(traineeId);



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).deleteTrainee(traineeId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTraineeById() {


        int traineeId = 1;
        Trainee trainee = new Trainee();
        when(traineeService.getTrainee(traineeId)).thenReturn(trainee);



        ResponseEntity<Trainee> responseEntity = traineeController.getTrainee(traineeId);



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).getTrainee(traineeId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTraineeByUsername() {


        String username = "testUser";
        Trainee trainee = new Trainee();
        when(traineeService.getTrainee(username)).thenReturn(trainee);



        ResponseEntity<Trainee> responseEntity = traineeController.getTrainee(username);



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).getTrainee(username);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testListAllTrainees() {


        List<Trainee> trainees = Arrays.asList(new Trainee(), new Trainee());
        when(traineeService.listAllTrainees()).thenReturn(trainees);



        ResponseEntity<List<Trainee>> responseEntity = traineeController.listAllTrainees();



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).listAllTrainees();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testActivateAndDeactivateTrainee() {


        int traineeId = 1;



        ResponseEntity<String> responseEntity = traineeController.activateandDeactivateTrainee(traineeId);



        verify(customMetricsService).recordCustomMetric(1);
        verify(traineeService).activateAndDeactivate(traineeId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
