package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.domains.Training;
import com.uktamjon.sodikov.dtos.TrainerWorkload;
import com.uktamjon.sodikov.services.TrainerWorkloadFeignClient;
import com.uktamjon.sodikov.services.TrainingService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final CustomMetricsService customMetricsService;
    @PostMapping("/create")
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        customMetricsService.recordCustomMetric(1);
        Training createdTraining = trainingService.createTraining(training);

        if (createdTraining != null) {
            return new ResponseEntity<>(createdTraining, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{trainingId}")
    public ResponseEntity<Training> getTraining(@PathVariable int trainingId) {
        customMetricsService.recordCustomMetric(1);
        Training training = trainingService.getTraining(trainingId);
        if (training != null) {
            return new ResponseEntity<>(training, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
