package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.*;
import com.uktamjon.sodikov.services.TraineeService;
import com.uktamjon.sodikov.services.TrainerService;
import com.uktamjon.sodikov.services.TrainingService;
import com.uktamjon.sodikov.services.TrainingTypeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TrainingStepDefs {
    @Autowired
    private TrainingService trainingService;

    @Autowired
    private TrainingTypeService trainingTypeService;
    @Autowired
    private TraineeService traineeService;

    @Autowired
    private TrainerService trainerService;



    private Trainee trainee;
    private Trainer trainer;
    private Training training;
    private TrainingType trainingType;
    private User trainerUser;
    private User traineeUser;
    private List<Training> trainings;

    @Given("a training with trainer first name {string} and trainer last name {string} and trainee first name {string} and trainee last name {string} and training name {string}")
    public void aTrainingWithTrainerFirstNameAndTrainerLastNameAndTraineeFirstNameAndTraineeLastNameAndTrainingName(String arg0, String arg1, String arg2, String arg3, String arg4) {
        trainerUser = User.builder()
                .firstName(arg0)
                .lastName(arg1)
                .build();
        trainer = Trainer.builder()
                .userId(trainerUser)
                .build();
        trainerService.createTrainer(trainer);

        traineeUser = User.builder()
                .firstName(arg2)
                .lastName(arg3)
                .build();
        trainee = Trainee.builder()
                .userId(traineeUser)
                .build();
        traineeService.createTrainee(trainee);

        trainingType = TrainingType.builder().trainingTypeName(arg4).build();
        trainingTypeService.createTrainingType(trainingType);
        training = Training.builder()
                .trainingName("SomeName")
                .trainingType(trainingType)
                .traineeId(Collections.singletonList(trainee))
                .trainerId(trainer)
                .trainingDate(LocalDateTime.now())
                .trainingDuration(1450000)
                .build();


    }

    @When("I create the training")
    public void iCreateTheTraining() {
        trainingService.createTraining(training);
    }

    @Then("training should be created in database")
    public void trainingShouldBeCreatedInDatabase() {
        assertNotNull(trainingService.getTraining(1));
        assertNotNull(trainingTypeService.getTrainingTypeByTrainingName(trainingType.getTrainingTypeName()));
        assertNotNull(trainerService.getTrainer("Isaac.Newton"));
        assertNotNull(traineeService.getTrainee("Some.Something"));
    }

    @When("all trainings are requested")
    public void allTrainingsAreRequested() {
        trainings=trainingService.getAllTrainings();
    }

    @Then("all trainings should be returned")
    public void allTrainingsShouldBeReturned() {
        assertEquals(trainings.size(),1);
    }

    @When("I delete the trainee by ID {int}")
    public void iDeleteTheTraineeByID(int arg0) {
        trainingService.deleteTraining(arg0);
    }

    @Then("the trainee by ID {int} should be deleted successfully")
    public void theTraineeShouldBeDeletedSuccessfully(int id) {
        trainings=trainingService.getAllTrainings();
        assertEquals(trainings.size(),0);
        assertNull(trainingService.getTraining(id));
    }
}
