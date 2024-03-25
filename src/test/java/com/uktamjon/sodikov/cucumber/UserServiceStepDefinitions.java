package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.services.UserService;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceStepDefinitions {

    @Autowired
    private UserService userService;

    private User user;
    private User retrievedUser;
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;





    @Given("a user with ID {int} with first name {string} and last name {string}")
    public void aUserWithIDWithFirstNameAndLastName(int arg0, String arg1, String arg2) {
        user=new User();
        user.setFirstName(arg1);
        user.setLastName(arg2);
        user.setId(arg0);
    }

    @When("the user is created")
    public void theUserisCreated(){
        userService.createUser(user);
    }

    @Then("the user should be persisted with a generated username")
    public void theUserShouldBePersistedWithAGeneratedUsername() {
        User userById = userService.getUserById(user.getId());
        assertEquals(userById.getUsername(), "John.Doe");
    }

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



    @Given("a user with ID {int}")
    public void setUserPassword(int userId) {
        user = new User();
        user.setId(userId);
        user.setFirstName("Some");
        user.setLastName("Some");
        userService.createUser(user);
    }

    @When("the user's password is updated to {string}")
    public void updateUserPassword(String newPassword) {
        user.setPassword(newPassword);
        userService.changePassword(newPassword, user.getId());
    }

    @Then("the user's password should be updated in the database")
    public void verifyPasswordUpdatedInDatabase() {
        User updatedUser = userService.getUserById(user.getId());
        assertTrue(passwordGeneratorService.checkPassword(user.getPassword(), updatedUser.getPassword()));
    }

    @When("the user with ID {int} is deleted")
    public void deleteUser(int userId) {
        userService.deleteUserById(userId);
    }

    @Then("the user with ID {int} should no longer exist in the database")
    public void verifyUserDeleted(int userId) {
        assertNull(userService.getUserById(userId));
    }


    @When("all users are requested")
    public void allUsersAreRequested() {
    }

    @Then("all users should be returned")
    public void allUsersShouldBeReturned() {
        assertNotNull(userService.getAllUsers());
    }


    @Given("a user with ID {int} and first name {string} and last name {string} and role {string}")
    public void aUserWithIDAndFirstNameAndLastNameAndRole(int arg0, String arg1, String arg2, String arg3) {
        user=new User();
        user.setId(arg0);
        user.setLastName(arg1);
        user.setFirstName(arg2);
        user.setRole(arg3);
        userService.createUser(user);
    }

    @When("the user's first name is updated to {string} and last name is updated to {string} and role is updated to {string}")
    public void theUserSFirstNameIsUpdatedToAndLastNameIsUpdatedToAndRoleIsUpdatedTo(String arg0, String arg1, String arg2) {
        user.setLastName(arg0);
        user.setFirstName(arg1);
        user.setRole(arg2);
        userService.updateUser(user);
    }

    @Then("the user's first name ans last name should be updated in the database")
    public void theUserSFirstNameAnsLastNameShouldBeUpdatedInTheDatabase() {
        User updatedUser = userService.getUserById(user.getId());
        assertEquals(user.getLastName(), updatedUser.getLastName());
        assertEquals(user.getFirstName(), updatedUser.getFirstName());
        assertEquals(user.getRole(), updatedUser.getRole());
    }
}
