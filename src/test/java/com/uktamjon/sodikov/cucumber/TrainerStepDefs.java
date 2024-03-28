package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TraineeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TrainerStepDefs {

    @Autowired
    private TraineeService trainerService;

    private Trainee trainer;
    private User user;
    private CreateResponse createResponse;

    @Given("a trainer with first name {string} and last name {string} and password {string}")
    public void aTraineeWithUsernameAndPassword(String firstName, String lastName, String password) {
        trainer = new Trainee();
        user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();
        trainer.setUserId(user);
    }

    @When("I create the trainer")
    public void iCreateTheTrainee() {
        createResponse = trainerService.createTrainee(trainer);
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
        trainer = Trainee.builder()
                .userId(user)
                .id(1)
                .build();
        trainerService.updateTrainee(trainer);
    }

    @Then("the trainer information should be updated successfully")
    public void trainerInformationShouldBeUpdated() {
        Trainee trainer1 = trainerService.getTrainee(user.getUsername());
        assertNotNull(trainer1);
        assertEquals(trainer1.getUserId().getUsername(), user.getUsername());
        System.out.println(trainer1.getUserId().getLastName());
        assertEquals(trainer1.getUserId().getLastName(), "Robertson");
    }

    @When("I delete the trainer {string}")
    public void i_delete_the_trainer(String username) {
        Trainee trainer1 = trainerService.getTrainee(username);
        trainerService.deleteTrainee(trainer1.getId());
    }

    @Then("the trainer should be deleted successfully {string}")
    public void the_trainer_should_be_deleted_successfully(String username) {
        assertNull(trainerService.getTrainee(username));
    }

    @When("all trainers are requested")
    public void allTrainersAreRequested() {
    }

    @Then("all trainers should be returned")
    public void allTrainersShouldBeReturned() {
        assertEquals(trainerService.listAllTrainees().size(),1);
    }
}
