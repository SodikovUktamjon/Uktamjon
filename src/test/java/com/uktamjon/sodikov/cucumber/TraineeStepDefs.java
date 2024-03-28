package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.services.TraineeService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class TraineeStepDefs {

    @Autowired
    private TraineeService traineeService;

    private Trainee trainee;
    private User user;
    private CreateResponse createResponse;

    @Given("a trainee with first name {string} and last name {string} and password {string}")
    public void aTraineeWithUsernameAndPassword(String firstName, String lastName, String password) {
        trainee = new Trainee();
        user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .build();
        trainee.setUserId(user);
    }

    @When("I create the trainee")
    public void iCreateTheTrainee() {
        createResponse = traineeService.createTrainee(trainee);
        user.setUsername(createResponse.getUsername());
    }

    @Then("the response should contain the username {string}")
    public void theResponseShouldContainTheUsernameAndPassword(String username) {
        assertEquals(username, createResponse.getUsername());
    }

    @When("I update the trainee with username {string}")
    public void updatingTheTrainee(String username) {
        user = new User();
        user.setLastName("Robertson");
        user.setFirstName("Isaac");
        user.setUsername(username);
        user.setId(1);
        trainee = Trainee.builder()
                .userId(user)
                .id(1)
                .build();
        traineeService.updateTrainee(trainee);
    }

    @Then("the trainee information should be updated successfully")
    public void traineeInformationShouldBeUpdated() {
        Trainee trainee1 = traineeService.getTrainee(user.getUsername());
        assertNotNull(trainee1);
        assertEquals(trainee1.getUserId().getUsername(), user.getUsername());
        System.out.println(trainee1.getUserId().getLastName());
        assertEquals(trainee1.getUserId().getLastName(), "Robertson");
    }

    @When("all trainees are requested")
    public void allTrainersAreRequested() {
    }

    @Then("all trainees should be returned")
    public void allTrainersShouldBeReturned() {
        assertEquals(traineeService.listAllTrainees().size(),1);
    }

    @When("I delete the trainee {string}")
    public void i_delete_the_trainee(String username) {
        Trainee trainee1 = traineeService.getTrainee(username);
        traineeService.deleteTrainee(trainee1.getId());
    }

    @Then("the trainee should be deleted successfully {string}")
    public void the_trainee_should_be_deleted_successfully(String username) {
        assertNull(traineeService.getTrainee(username));
    }
}
