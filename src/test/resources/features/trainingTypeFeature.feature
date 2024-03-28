Feature: Training Type Management

  Scenario: Create a new training type
    Given a training type with name "Software Development"
    When I call the createTrainingType API
    Then the training type is saved successfully

  Scenario: Update an existing training type
    Given a training type with ID 1 and name "Software Development" exists in the database
    When I call the updateTrainingType API with the updated name "Web Development"
    Then the training type is updated successfully

#  Scenario: Update a training type with a name that already exists
#    Given a training type with ID 1 and name "Software Development" exists in the database
#    Given another training type with name "Web Development" exists in the database
#    When I call the updateTrainingType API with ID 1 and name "Web Development"
#    Then the training type is not updated
#
#  Scenario: Delete a training type by ID
#    Given a training type with ID 1 exists in the database
#    When I call the deleteTrainingType API with ID 1
#    Then the training type is deleted successfully
#
#  Scenario: Get a training type by a non-existent ID
#    When I call the getTrainingTypeById API with ID 99
#    Then the response status code indicates not found
#
#  Scenario: Get all training types
#    Given multiple training types exist in the database
#    When I call the getAllTrainingTypes API
#    Then the response contains a list of all training types
