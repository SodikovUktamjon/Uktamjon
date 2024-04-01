package com.uktamjon.sodikov.acucumber;

import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.services.TrainingTypeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

import java.util.List;

public class TrainingTypeStepDefinitions {
    @Autowired
    private TrainingTypeService trainingTypeService;
    private TrainingType trainingType;
    private List<TrainingType> trainingTypes;

    @Given("a training type with name {string}")
    public void aTrainingTypeWithNameExistsInTheDatabase(String name) {
        trainingType = new TrainingType();
        trainingType.setTrainingTypeName(name);
    }

    @When("I call the createTrainingType API")
    public void iCallTheCreateTrainingTypeAPI() {
        trainingTypeService.createTrainingType(trainingType);
    }

    @Then("the training type is saved successfully")
    public void theTrainingTypeIsSavedSuccessfully() {
        assertEquals(trainingType.getTrainingTypeName(), trainingTypeService.getTrainingTypeByTrainingName(trainingType.getTrainingTypeName()).getTrainingTypeName());
    }



    @When("I call the updateTrainingType API with the updated name {string}")
    public void iCallTheUpdateTrainingTypeAPIWithUpdatedName(String updatedName) {
        trainingType=new TrainingType();
        trainingType.setTrainingTypeName(updatedName);
        trainingTypeService.updateTrainingType(trainingType);
    }

    @Then("the training type is updated successfully")
    public void theTrainingTypeIsUpdated() {
        assertEquals(trainingType.getTrainingTypeName(), trainingTypeService.getTrainingTypeById(trainingType.getId()).getTrainingTypeName());
    }


    @When("I call the getAllTrainingTypes API")
    public void iCallTheGetAllTrainingTypesAPI() {
        trainingTypes=trainingTypeService.getAllTrainingTypes();
    }

    @Then("the response contains a list of all training types")
    public void listAllTypes(){
        assertEquals(trainingTypes.size(),2);
    }



    @Given("training types with name {string} and name {string} that exist in the database")
    public void trainingTypesWithIDAndIDThatExistInTheDatabase(String arg0, String arg1) {
        assertNotNull(trainingTypeService.getTrainingTypeByTrainingName(arg0));
        assertNotNull(trainingTypeService.getTrainingTypeByTrainingName(arg1));
    }

    @When("I call the deleteTrainingType API with name {string} and name {string}")
    public void iCallTheDeleteTrainingTypeAPIWithIDAndWithID(String arg0, String arg1) {
        trainingTypeService.deleteTrainingType(arg0);
        trainingTypeService.deleteTrainingType(arg1);
    }

    @Then("training types is deleted successfully")
    public void trainingTypesIsDeletedSuccessfully() {
        assertEquals(trainingTypeService.getAllTrainingTypes().size(),0);
    }

}
