Feature: Trainer Service Features

  Scenario: Create Trainer
    Given a trainer with first name "Abraham" and last name "Lincoln" and password "Some123@Some"
    When I create the trainer
    Then the trainer response should contain the username "Abraham.Lincoln"

  Scenario: Update Trainee
    When I update the trainer with username "Abraham.Lincoln"
    Then the trainer information should be updated successfully

  Scenario: Getting all trainers
    When all trainers are requested
    Then all trainers should be returned

  Scenario: Delete Trainee
    When I delete the trainer "Abraham.Lincoln"
    Then the trainer should be deleted successfully "Abraham.Lincoln"
