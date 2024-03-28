package com.uktamjon.sodikov.cucumber;

import com.uktamjon.sodikov.domains.TrainingType;
import com.uktamjon.sodikov.services.TrainingTypeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
import java.util.ArrayList;
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

    @Given("a training type with ID {int} and name {string} exists in the database")
    public void aTrainingTypeWithIDAndNameExistsInTheDatabase(int id, String name) {
        trainingType = new TrainingType();
        trainingType.setId(id);
        trainingType.setTrainingTypeName(name);
        assertNotNull(trainingTypeService.getTrainingTypeById(id));
    }

    @When("I call the updateTrainingType API with the updated name {string}")
    public void iCallTheUpdateTrainingTypeAPIWithUpdatedName(String updatedName) {
        trainingType.setTrainingTypeName(updatedName);
        trainingTypeService.updateTrainingType(trainingType);
    }

    @Then("the training type is updated successfully")
    public void theTrainingTypeIsUpdated() {
        assertEquals(trainingType.getTrainingTypeName(), trainingTypeService.getTrainingTypeById(trainingType.getId()).getTrainingTypeName());
    }
//    @Given("multiple training types exist in the database")
//    public void multipleTrainingTypesExistInTheDatabase() {
//        trainingTypes = new ArrayList<>();
//        trainingTypes.add(new TrainingType(1, "Software Development"));
//        trainingTypes.add(new TrainingType(2, "Web Development"));
//        // Simulate multiple training types in the database (modify based on your implementation)
//        Mockito.when(trainingTypeService.getAllTrainingTypes()).thenReturn(trainingTypes);
//    }
//
//
//
//    @When("I call the deleteTrainingType API with ID (\\d+)")
//    public void iCallTheDeleteTrainingTypeAPIWithID(int id) {
//        trainingTypeService.deleteTrainingType(id);
//    }
//
//    @When("I call the getTrainingTypeById API with ID (\\d+)")
//    public void iCallTheGetTrainingTypeByIdAPIWithID(int id) {
//        Mockito.when(trainingTypeService.getTrainingTypeById(id)).thenReturn(trainingType);
//    }
//
//    @When("I call the getAllTrainingTypes API")
//    public void iCallTheGetAllTrainingTypesAPI() {
//        trainingTypeService.getAllTrainingTypes();
//    }
//
//
//    @Then("the response contains the created training type with name \"([^\"]*)\"")
//    public void theResponseContainsTheCreatedTrainingTypeWithName(String name) {
//        // Assert the response object and verify the name
//    }
//
//    @Then("the training type is updated successfully")
//    public void theTrainingTypeIsUpdatedSuccessfully() {
//        // Verify successful update using Mockito verification
//        Mockito.verify(trainingTypeService).save(trainingType);
//    }
//
//    @Then("the training type is not updated")
//    public void theTrainingTypeIsNotUpdated() {
//        // Verify no update using Mockito verification
//        Mockito.verify(trainingTypeService, Mockito.never()).save(trainingType);
//    }
//
//    @Then("the response status code indicates not found")
}
