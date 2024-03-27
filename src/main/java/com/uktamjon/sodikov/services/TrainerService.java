package com.uktamjon.sodikov.services;

import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.repository.TrainerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainingTypeService trainingTypeService;
    private final UserService userService;

    @Transactional
    public CreateResponse createTrainer(Trainer trainer) {
        if (trainingTypeService.getTrainingTypeById(trainer.getSpecialization()) != null) {
            log.error("Trainer not created: {}", trainer);
            return null;
        }
        if (userService.getUserById(trainer.getUserId().getId()) != null) {
            log.error("Trainer not created: {}", trainer);
            return null;
        }
        User user1 = userService.createUser(trainer.getUserId());
        trainer.setUserId(user1);
        log.info("Trainer created: {}", trainer);
        Trainer save = trainerRepository.save(trainer);
        return CreateResponse.builder()
                .username(save.getUserId().getUsername())
                .password(save.getUserId().getPassword())
                .build();
    }

    public boolean updateTrainer(Trainer trainer) {
        if (trainingTypeService.getTrainingTypeById(trainer.getSpecialization()) != null) {
            log.error("Trainer not updated: {}", trainer);
            return false;
        }
        if (trainerRepository.existsById(trainer.getId())) {

            userService.updateUser(trainer.getUserId());
            trainerRepository.save(trainer);
            log.info("Trainer updated: {}", trainer);
            return true;
        }
        return false;
    }


    public Trainer getTrainer(int trainerId) {
        log.info("Getting trainer by id {}", trainerId);
        return trainerRepository.findByUserId(trainerId);
    }

    public Trainer getTrainer(String username) {
        log.info("Getting trainer by username {}", username);
        User user = userService.getUserByUsername(username);
        return trainerRepository.findByUserId(user.getId());
    }

    public boolean changePassword(String password, String username) {
        log.info("Changing password for user {}", username);
        return userService.changePassword(password, username);
    }

    public boolean changePassword(String password, int id) {
        log.info("Changing password for user {}", id);
        return userService.changePassword(password, id);
    }

    public boolean activateAndDeactivate(String username) {
        log.info("Changing status for user {}", username);
        return userService.activateAndDeactivate(username);
    }

    public void activateAndDeactivate(int id) {
        log.info("Changing status for user {}", id);
        userService.activateAndDeactivate(id);
    }

    public List<Trainer> getAllTrainers() {
        log.info("Getting all trainers");
        return trainerRepository.findAll();
    }

    public List<Trainer> getTrainersNotAssignedAndActive() {
        log.info("Getting trainers not assigned and active");
        return trainerRepository.findAllByUserId_Active(true);
    }


}
