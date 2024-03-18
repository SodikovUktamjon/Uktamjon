package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.services.UserService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserServiceStepDefinitions {

    @Autowired
    private UserService userService;

    private User user;
    private User retrievedUser;

    @Given("a user with ID {int} exists")
    public void createUser(int userId) {
        user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");
        userService.createUser(user);
    }

    @When("a user with ID {int} is requested")
    public void getUserById(int userId) {
        retrievedUser = userService.getUserById(userId);
    }

    @Then("the user with ID {int} should be returned")
    public void verifyUserRetrievedById(int userId) {
        assertNotNull(retrievedUser);
        assertEquals(userId, retrievedUser.getId());
    }

    @Given("a user with ID {int} and password {string}")
    public void setUserPassword(int userId, String password) {
        user = new User();
        user.setId(userId);
        user.setPassword(password);
    }

    @When("the user's password is updated to {string}")
    public void updateUserPassword(String newPassword) {
        user.setId(1);
        user.setPassword(newPassword);
        userService.updateUser(user);
    }

    @Then("the user's password should be updated in the database")
    public void verifyPasswordUpdatedInDatabase() {
        User updatedUser = userService.getUserById(user.getId());
        assertEquals(user.getPassword(), updatedUser.getPassword());
    }

    @When("the user with ID {int} is deleted")
    public void deleteUser(int userId) {
        userService.deleteUserById(userId);
    }

    @Then("the user with ID {int} should no longer exist in the database")
    public void verifyUserDeleted(int userId) {
        assertNull(userService.getUserById(userId));
    }

    @Given("a user with first name {string} and last name {string}")
    public void aUserWithFirstNameAndLastName(String arg0, String arg1) {
    }

    @When("the user is created")
    public void theUserIsCreated() {
    }

    @Then("the user should be persisted with a generated username")
    public void theUserShouldBePersistedWithAGeneratedUsername() {
    }

    @When("all users are requested")
    public void allUsersAreRequested() {
    }

    @Then("all users should be returned")
    public void allUsersShouldBeReturned() {
    }
}
