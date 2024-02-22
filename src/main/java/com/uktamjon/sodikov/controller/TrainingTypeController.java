package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.services.TrainingTypeService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-types")
@RequiredArgsConstructor
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;
    private final CustomMetricsService customMetricsService;

    @GetMapping("/{trainingTypeId}")
    public ResponseEntity<TrainingType> getTrainingTypeById(@PathVariable int trainingTypeId) {
        customMetricsService.recordCustomMetric(1);
        TrainingType trainingType = trainingTypeService.getTrainingTypeById(trainingTypeId);

        return (trainingType != null)
                ? new ResponseEntity<>(trainingType, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/listAll")
    public ResponseEntity<List<TrainingType>> getAllTrainingTypes() {
        customMetricsService.recordCustomMetric(1);
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<TrainingType> createTrainingType(@RequestBody TrainingType trainingType) {
        customMetricsService.recordCustomMetric(1);
        TrainingType createdTrainingType = trainingTypeService.createTrainingType(trainingType);
        return new ResponseEntity<>(createdTrainingType, HttpStatus.CREATED);
    }
}
