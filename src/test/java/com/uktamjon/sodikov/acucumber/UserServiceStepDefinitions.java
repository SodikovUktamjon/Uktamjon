package com.uktamjon.sodikov.acucumber;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.services.UserService;
import com.uktamjon.sodikov.utils.PasswordGeneratorService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

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
        System.out.println(userById.getUsername());
        assertEquals(userById.getUsername(), "John.Doe");
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


    @When("the user's password is updated to {string} with ID {int}")
    public void updateUserPassword(String newPassword, int id) {
        user= User.builder().password(newPassword).build();
        userService.changePassword(newPassword, id);
    }

    @Then("the user's password should be updated in the database")
    public void verifyPasswordUpdatedInDatabase() {
        User updatedUser = userService.getUserById(1);
        assertTrue(passwordGeneratorService.checkPassword(user.getPassword(), updatedUser.getPassword()));
    }

    @When("all users are requested")
    public void allUsersAreRequested() {
    }

    @Then("all users should be returned")
    public void allUsersShouldBeReturned() {
        assertNotNull(userService.getAllUsers());
    }




    @When("the user's first name is updated to {string} and last name is updated to {string} and role is updated to {string}")
    public void theUserSFirstNameIsUpdatedToAndLastNameIsUpdatedToAndRoleIsUpdatedTo(String arg0, String arg1, String arg2) {
        user=userService.getUserById(1);
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


    @When("the user with username {string} is activated")
    public void userActivated(String userId) {
        userService.activateAndDeactivate(userId);
    }

    @Then("the user with username {string} should be active in database")
    public void userActivatedInDatabase(String userId) {
        assertTrue(userService.getUserByUsername(userId).isActive());
    }


}
