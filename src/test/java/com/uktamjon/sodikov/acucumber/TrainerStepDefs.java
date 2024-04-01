package com.uktamjon.sodikov.acucumber;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.Trainer;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TraineeService;
import com.uktamjon.sodikov.services.TrainerService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TrainerStepDefs {

    @Autowired
    private TrainerService trainerService;

    private Trainer trainer;
    private User user;
    private CreateResponse createResponse;

    @Given("a trainer with first name {string} and last name {string} and password {string}")
    public void aTraineeWithUsernameAndPassword(String firstName, String lastName, String password) {
        trainer = new Trainer();
        user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();
        trainer.setUserId(user);
    }

    @When("I create the trainer")
    public void iCreateTheTrainee() {
        createResponse = trainerService.createTrainer(trainer);
        user.setUsername(createResponse.getUsername());
    }

    @Then("the trainer response should contain the username {string}")
    public void theResponseShouldContainTheUsernameAndPassword(String username) {
        assertEquals(username, createResponse.getUsername());
    }

    @When("I update the trainer with username {string}")
    public void updatingTheTrainee(String username) {
        user = new User();
        user.setLastName("Robertson");
        user.setFirstName("Isaac");
        user.setUsername(username);
        user.setId(1);
        trainer = Trainer.builder()
                .userId(user)
                .id(1)
                .build();
        trainerService.updateTrainer(trainer);
    }

    @Then("the trainer information should be updated successfully")
    public void trainerInformationShouldBeUpdated() {
        Trainer trainer1 = trainerService.getTrainer(user.getUsername());
        assertNotNull(trainer1);
        assertEquals(trainer1.getUserId().getUsername(), user.getUsername());
        System.out.println(trainer1.getUserId().getLastName());
        System.out.println(trainer1.getUserId().getUsername());
        assertEquals(trainer1.getUserId().getLastName(), "Robertson");
    }

    @When("I delete the trainer {string}")
    public void i_delete_the_trainer(String username) {
      trainerService.deleteTrainee(username);
    }

    @Then("the trainer should be deleted successfully {string}")
    public void the_trainer_should_be_deleted_successfully(String username) {
        assertNull(trainerService.getTrainer(username));
    }

    @When("all trainers are requested")
    public void allTrainersAreRequested() {
    }

    @Then("all trainers should be returned")
    public void allTrainersShouldBeReturned() {
        assertFalse(trainerService.getAllTrainers().isEmpty());
    }
}
