package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.repository.TrainingTypeRepository;
import com.uktamjon.sodikov.services.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeService trainingTypeService;


    @BeforeEach
    void setUp() {
        trainingTypeRepository = mock(TrainingTypeRepository.class);
        trainingTypeService = new TrainingTypeService(trainingTypeRepository);
    }


    @Test
    public void testCreateTrainingType() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.save(trainingType)).thenReturn(trainingType);
        TrainingType createdTrainingType = trainingTypeService.createTrainingType(trainingType);
        assertEquals(trainingType, createdTrainingType);
    }

    @Test
    public void testUpdateTrainingType_NotExisting() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.existsByTrainingTypeName(trainingType.getTrainingTypeName())).thenReturn(false);
        when(trainingTypeRepository.save(trainingType)).thenReturn(trainingType);
        TrainingType updatedTrainingType = trainingTypeService.updateTrainingType(trainingType);
        assertEquals(trainingType, updatedTrainingType);
    }

    @Test
    public void testUpdateTrainingType_Existing() {
        TrainingType trainingType = new TrainingType();
        when(trainingTypeRepository.existsByTrainingTypeName(trainingType.getTrainingTypeName())).thenReturn(true);
        TrainingType updatedTrainingType = trainingTypeService.updateTrainingType(trainingType);
        assertNull(updatedTrainingType);
    }

    @Test
    public void testDeleteTrainingType() {
        int trainingTypeId = 1;
        trainingTypeService.deleteTrainingType(trainingTypeId);
    }

    @Test
    public void testGetTrainingTypeById_TrainingTypeExists() {
        int trainingTypeId = 1;
        TrainingType expectedTrainingType = new TrainingType();
        expectedTrainingType.setId(trainingTypeId);
        when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(expectedTrainingType));
        TrainingType retrievedTrainingType = trainingTypeService.getTrainingTypeById(trainingTypeId);
        assertEquals(expectedTrainingType, retrievedTrainingType);
    }

    @Test
    public void testGetTrainingTypeById_TrainingTypeDoesNotExist() {
        int trainingTypeId = 1;
        when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.empty());
        TrainingType retrievedTrainingType = trainingTypeService.getTrainingTypeById(trainingTypeId);
        assertNull(retrievedTrainingType);
    }

    @Test
    public void testGetAllTrainingTypes() {
        List<TrainingType> expectedTrainingTypes = Collections.singletonList(new TrainingType());
        when(trainingTypeRepository.findAll()).thenReturn(expectedTrainingTypes);
        List<TrainingType> retrievedTrainingTypes = trainingTypeService.getAllTrainingTypes();
        assertEquals(expectedTrainingTypes, retrievedTrainingTypes);
    }
}
