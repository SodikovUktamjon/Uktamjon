package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TrainerService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainers")
@RequiredArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private final CustomMetricsService customMetricsService;

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> createTrainer(@RequestBody Trainer trainer) {
        customMetricsService.recordCustomMetric(1);
        CreateResponse createdTrainer = trainerService.createTrainer(trainer);
        return (createdTrainer != null)?new ResponseEntity<>(createdTrainer, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update/{trainerId}")
    public ResponseEntity<String> updateTrainer(@RequestBody Trainer trainer) {
        customMetricsService.recordCustomMetric(1);
        boolean updated = trainerService.updateTrainer(trainer);
        if (updated) {
            return new ResponseEntity<>("Trainer updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Trainer update failed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{trainerId}")
    public ResponseEntity<Trainer> getTrainer(@PathVariable int trainerId) {
        customMetricsService.recordCustomMetric(1);
        Trainer trainer = trainerService.getTrainer(trainerId);
        if (trainer != null) {
            return new ResponseEntity<>(trainer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<Trainer> getTrainerByUsername(@PathVariable String username) {
        customMetricsService.recordCustomMetric(1);
        Trainer trainer = trainerService.getTrainer(username);
        if (trainer != null) {
            return new ResponseEntity<>(trainer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Trainer>> getAllTrainers() {
        customMetricsService.recordCustomMetric(1);
        List<Trainer> trainers = trainerService.getAllTrainers();
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }
    @PatchMapping("/activateOrDeactivate/{trainerId}")
    public ResponseEntity<String> activateandDeactivateTrainer(@PathVariable int trainerId) {
        customMetricsService.recordCustomMetric(1);
        trainerService.activateAndDeactivate(trainerId);
        return new ResponseEntity<>("Trainer with ID " + trainerId + " has been activated", HttpStatus.OK);
    }


    @GetMapping("/listAllActiveAndNotAssigned")
    public ResponseEntity<List<Trainer>> getAllActiveAndNotAssignedTrainers() {
        customMetricsService.recordCustomMetric(1);
        List<Trainer> trainers = trainerService.getTrainersNotAssignedAndActive();
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }


}
