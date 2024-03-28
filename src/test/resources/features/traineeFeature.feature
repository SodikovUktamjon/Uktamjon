Feature: Trainee Service Features

  Scenario: Create Trainee
    Given a trainee with first name "Abraham" and last name "Lincoln" and password "Some123@Some"
    When I create the trainee
    Then the response should contain the username "Abraham.Lincoln"

  Scenario: Update Trainee
    When I update the trainee with username "Abraham.Lincoln"
    Then the trainee information should be updated successfully

  Scenario: Getting all trainees
    When all trainees are requested
    Then all trainees should be returned


  Scenario: Delete Trainee
    When I delete the trainee "Abraham.Lincoln"
    Then the trainee should be deleted successfully "Abraham.Lincoln"
