package com.uktamjon.sodikov.controller;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TraineeService;
import com.uktamjon.sodikov.utils.CustomMetricsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainees")
@Slf4j
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;
    private final CustomMetricsService customMetricsService;

    @PostMapping("/create")
    public ResponseEntity<CreateResponse> createTrainee(@RequestBody Trainee trainee) {
        customMetricsService.recordCustomMetric(1);
        CreateResponse createdTrainee = traineeService.createTrainee(trainee);
        return (createdTrainee != null)
                ? new ResponseEntity<>(createdTrainee, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/update/{traineeId}")
    public ResponseEntity<Trainee> updateTrainee(@RequestBody Trainee trainee) {
        customMetricsService.recordCustomMetric(1);
        Trainee updatedTrainee = traineeService.updateTrainee(trainee);

        return (updatedTrainee != null)
                ? new ResponseEntity<>(updatedTrainee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/delete/{traineeId}")
    public ResponseEntity<String> deleteTrainee(@PathVariable int traineeId) {
        customMetricsService.recordCustomMetric(1);
        traineeService.deleteTrainee(traineeId);
        return new ResponseEntity<>("Trainee with ID " + traineeId + " has been deleted", HttpStatus.OK);
    }

    @GetMapping("/{traineeId}")
    public ResponseEntity<Trainee> getTrainee(@PathVariable int traineeId) {
        customMetricsService.recordCustomMetric(1);
        Trainee trainee = traineeService.getTrainee(traineeId);

        return (trainee != null)
                ? new ResponseEntity<>(trainee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Trainee> getTrainee(@PathVariable String username) {
        customMetricsService.recordCustomMetric(1);
        Trainee trainee = traineeService.getTrainee(username);

        return (trainee != null)
                ? new ResponseEntity<>(trainee, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @GetMapping("/listAll")
    public ResponseEntity<List<Trainee>> listAllTrainees() {
        customMetricsService.recordCustomMetric(1);
        List<Trainee> trainees = traineeService.listAllTrainees();
        return new ResponseEntity<>(trainees, HttpStatus.OK);
    }

    @PatchMapping("/activateOrDeactivate/{traineeId}")
    public ResponseEntity<String> activateandDeactivateTrainee(@PathVariable int traineeId) {
        customMetricsService.recordCustomMetric(1);
        traineeService.activateAndDeactivate(traineeId);
        return new ResponseEntity<>("Trainee with ID " + traineeId + " has been activated", HttpStatus.OK);
    }

    
}
