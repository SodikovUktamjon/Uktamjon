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
    @Given("a training type with name {string} is created")
    public void multipleTrainingTypesExistInTheDatabase(String string) {
      trainingType=TrainingType.builder()
              .trainingTypeName(string)
              .build();
      trainingTypeService.createTrainingType(trainingType);
    }

    @When("I call the updateTrainingType API with ID {int} and name {string} to {string}")
    public void iCallTheDeleteTrainingTypeAPIWithID(int id, String name, String newName) {
        trainingTypeService.updateTrainingType(TrainingType.builder()
                        .trainingTypeName(newName)
                        .id(id)
                .build());
    }

    @Then("the training type is not updated and left like {string}")
    public void theTrainingTypeIsNotUpdated(String name) {
        String trainingTypeName = trainingTypeService.getTrainingTypeById(1).getTrainingTypeName();
        assertNotEquals(trainingTypeName, trainingType.getTrainingTypeName());
        assertEquals(trainingTypeName, name);
    }


    @When("I call the getAllTrainingTypes API")
    public void iCallTheGetAllTrainingTypesAPI() {
        trainingTypes=trainingTypeService.getAllTrainingTypes();
    }

    @Then("the response contains a list of all training types")
    public void listAllTypes(){
        assertEquals(trainingTypes.size(),2);
    }



    @Given("training types with ID {int} and ID {int} that exist in the database")
    public void trainingTypesWithIDAndIDThatExistInTheDatabase(int arg0, int arg1) {
        assertNotNull(trainingTypeService.getTrainingTypeById(arg0));
        assertNotNull(trainingTypeService.getTrainingTypeById(arg1));
    }

    @When("I call the deleteTrainingType API with ID {int} and with ID {int}")
    public void iCallTheDeleteTrainingTypeAPIWithIDAndWithID(int arg0, int arg1) {
        trainingTypeService.deleteTrainingType(arg0);
        trainingTypeService.deleteTrainingType(arg1);
    }

    @Then("training types is deleted successfully")
    public void trainingTypesIsDeletedSuccessfully() {
        assertEquals(trainingTypeService.getAllTrainingTypes().size(),0);
    }

}
