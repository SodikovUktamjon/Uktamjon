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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TraineeStepDefs {

    @Autowired
    private TraineeService traineeService;

    private Trainee trainee;
    private User user;
    private CreateResponse createResponse;

    @Given("a trainee with first name {string} and last name {string} and password {string}")
    public void aTraineeWithUsernameAndPassword(String firstName,String lastName, String password) {
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
    }

    @Then("the response should contain the username {string} and password {string}")
    public void theResponseShouldContainTheUsernameAndPassword(String username, String password) {
        assertEquals(username, createResponse.getUsername());
        assertEquals(password, createResponse.getPassword());
        assertNotNull(traineeService.getTrainee(user.getUsername()));
    }

}
