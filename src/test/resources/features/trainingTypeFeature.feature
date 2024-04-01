Feature: Training Type Management

  Scenario: Create a new training type
    Given a training type with name "Software Development"
    When I call the createTrainingType API
    Then the training type is saved successfully

  Scenario: Update an existing training type
    When I call the updateTrainingType API with the updated name "Web Development"
    Then the training type is updated successfully


    Scenario: Get all training types
    When I call the getAllTrainingTypes API
    Then the response contains a list of all training types



  Scenario: Delete a training type by ID
    Given training types with name "Software Development" and name "Web Development" that exist in the database
    When I call the deleteTrainingType API with name "Software Development" and name "Web Development"
    Then training types is deleted successfully


