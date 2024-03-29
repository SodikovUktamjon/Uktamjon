Feature: Training Type Management

  Scenario: Create a new training type
    Given a training type with name "Software Development"
    When I call the createTrainingType API
    Then the training type is saved successfully

  Scenario: Update an existing training type
    Given a training type with ID 1 and name "Software Development" exists in the database
    When I call the updateTrainingType API with the updated name "Web Development"
    Then the training type is updated successfully

  Scenario: Update a training type with a name that already exists
    Given a training type with name "Software Development" is created
    When I call the updateTrainingType API with ID 1 and name "Web Development" to "Software Development"
    Then the training type is not updated and left like "Web Development"

    Scenario: Get all training types
    When I call the getAllTrainingTypes API
    Then the response contains a list of all training types



  Scenario: Delete a training type by ID
    Given training types with ID 1 and ID 2 that exist in the database
    When I call the deleteTrainingType API with ID 1 and with ID 2
    Then training types is deleted successfully


