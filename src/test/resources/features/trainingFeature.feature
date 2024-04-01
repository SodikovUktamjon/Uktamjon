Feature: Training Service Features

  Scenario: Create Training
    Given a training with trainer first name "Isaac" and trainer last name "Newton" and trainee first name "Some" and trainee last name "Something" and training name "Web"
    When I create the training
    Then training should be created in database


  Scenario: Getting all trainings
    When all trainings are requested
    Then all trainings should be returned

  Scenario: Delete Training
    When I delete the trainee by ID 1
    Then the trainee by ID 1 should be deleted successfully
