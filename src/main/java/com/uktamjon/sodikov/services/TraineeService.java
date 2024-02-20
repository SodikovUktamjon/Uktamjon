package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.repository.TraineeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TraineeService {

    private final UserService userService;
    private final TraineeRepository traineeRepository;


    public CreateResponse createTrainee(Trainee trainee) {
        if (userService.getUserByUsername(trainee.getUser().getUsername()) != null) {
            log.info("User with this username already exists");
            return null;
        }
        User user1 = userService.createUser(trainee.getUser());
        trainee.setUser(user1);
        log.info("Trainee created: {}", trainee);
        Trainee save = traineeRepository.save(trainee);
        return CreateResponse.builder()
                .username(save.getUser().getUsername())
                .password(save.getUser().getPassword())
                .build();
    }

    public Trainee updateTrainee(Trainee trainee) {
        if (userService.getUserById(trainee.getUser().getId()) != null && traineeRepository.existsById(trainee.getId())) {
            userService.updateUser(trainee.getUser());
            log.info("Trainee updated: {}", trainee);
            return traineeRepository.save(trainee);
        }
        log.error("Trainee not updated: {}", trainee);
        return null;
    }

    public void deleteTrainee(int traineeId) {
        Trainee traineeNotFound = traineeRepository.findById(traineeId).orElseThrow(()->new NullPointerException("User Not Found"));
        userService.deleteUserById(Objects.requireNonNull(traineeNotFound).getUser().getId());
        traineeRepository.deleteById(traineeId);
        log.info("Trainee deleted: {}", traineeId);
    }

    public Trainee getTrainee(int traineeId) {
        log.info("Getting trainee by id {}", traineeId);
        return traineeRepository.findById(traineeId).orElse(null);
    }

    public Trainee getTrainee(String username){
        log.info("Getting trainee by username {}", username);
        User user = userService.getUserByUsername(username);
        return traineeRepository.findByUserId(user.getId());
    }

    public boolean changePassword(String password, String username){
        log.info("Changing password for user {}", username);
        return userService.changePassword(password, username);
    }
    public boolean changePassword(String password, int id){
        log.info("Changing password for user {}", id);
        return userService.changePassword(password, id);
    }
    public boolean activateAndDeactivate(String username) {
        log.info("Changing status for user {}", username);
        return userService.activateAndDeactivate(username);
    }

    public boolean activateAndDeactivate(int id) {
        log.info("Changing status for user {}", id);
        return userService.activateAndDeactivate(id);
    }

    public List<Trainee> listAllTrainees(){
        log.info("Getting all trainees");
        return traineeRepository.findAll();
    }



}
