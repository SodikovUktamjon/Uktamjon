package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateAuthUserDTO;
import com.uktamjon.sodikov.dtos.GetTokenDTO;
import com.uktamjon.sodikov.services.AuthService;
import com.uktamjon.sodikov.services.UserService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthServiceStepDefinitions {
    private User user;
    @Autowired
    private UserService userService;
    private CreateAuthUserDTO createAuthUserDTO;
    private GetTokenDTO getTokenDTO;
    @Autowired
    private AuthService authService;


    @Given("a user with username {string} and password {string}")
    public void aUserWithUsernameAndPassword(String arg0, String arg1) {
        user=new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        userService.createUser(user);
        createAuthUserDTO = new CreateAuthUserDTO();
        createAuthUserDTO.setUsername(arg0);
        createAuthUserDTO.setPassword(arg1);
        user.setPassword(arg1);
    }

    @When("the user logs in")
    public void theUserLogsIn() {
        userService.updateUser(user);
        getTokenDTO = authService.login(createAuthUserDTO);

    }
    @Then("a valid token is returned")
    public void aValidTokenIsReturned() {
        Assert.assertNotNull(getTokenDTO);
        Assert.assertNotNull(getTokenDTO.getToken());
    }


//    @Given("a user with username {string} and incorrect password {string}")
//    public void aUserWithUsernameAndIncorrectPassword(String arg0, String arg1) {
//        createAuthUserDTO = new CreateAuthUserDTO();
//        createAuthUserDTO.setUsername(arg0);
//        createAuthUserDTO.setPassword(arg1);
//    }
//    @And("the user is blocked")
//    public void theUserIsBlocked() {
//
//    }

//    @When("the user logs in")
//    public void theUserLogsInBlocked() {
//        getTokenDTO = authService.login(createAuthUserDTO);
//    }
//
//
//    @Then("no token is returned")
//    public void noTokenIsReturned() {
//        Assert.assertNull(getTokenDTO);
//    }
//
//
//    @Given("a non-existing user with username {string} and password {string}")
//    public void aNonExistingUserWithUsernameAndPassword(String arg0, String arg1) {
//    }


}
