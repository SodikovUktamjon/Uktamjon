package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.services.TrainingTypeService;
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
class TrainingTypeControllerTest {

    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private CustomMetricsService customMetricsService;

    @InjectMocks
    private TrainingTypeController trainingTypeController;

    @Test
    void testGetTrainingTypeById() {
        int trainingTypeId = 1;
        TrainingType trainingType = new TrainingType();
        when(trainingTypeService.getTrainingTypeById(trainingTypeId)).thenReturn(trainingType);

        ResponseEntity<TrainingType> responseEntity = trainingTypeController.getTrainingTypeById(trainingTypeId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingTypeService).getTrainingTypeById(trainingTypeId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetTrainingTypeByIdNotFound() {
        int trainingTypeId = 1;
        when(trainingTypeService.getTrainingTypeById(trainingTypeId)).thenReturn(null);

        ResponseEntity<TrainingType> responseEntity = trainingTypeController.getTrainingTypeById(trainingTypeId);

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingTypeService).getTrainingTypeById(trainingTypeId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetAllTrainingTypes() {
        List<TrainingType> trainingTypes = Arrays.asList(new TrainingType(), new TrainingType());
        when(trainingTypeService.getAllTrainingTypes()).thenReturn(trainingTypes);

        ResponseEntity<List<TrainingType>> responseEntity = trainingTypeController.getAllTrainingTypes();

        verify(customMetricsService).recordCustomMetric(1);
        verify(trainingTypeService).getAllTrainingTypes();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
