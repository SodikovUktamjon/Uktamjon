Feature: Trainee Service Features

  Scenario: Create Trainee
    Given a trainee with first name "Abraham" and last name "Lincoln" and password "Some123@Some"
    When I create the trainee
    Then the response should contain the username "Abraham.Lincoln"

  Scenario: Update Trainee
    Given an existing trainee with username "testuser" and password "password"
    When I update the trainee
    Then the trainee information should be updated successfully

  Scenario: Delete Trainee
    Given an existing trainee with username "testuser" and password "password"
    When I delete the trainee
    Then the trainee should be deleted successfully
