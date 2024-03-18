Feature: User Service Feature

  Scenario: Creating a user
    Given a user with first name "John" and last name "Doe"
    When the user is created
    Then the user should be persisted with a generated username

  Scenario: Getting all users
    When all users are requested
    Then all users should be returned

  Scenario: Getting a user by ID
    Given a user with ID 1 exists
    When a user with ID 1 is requested
    Then the user with ID 1 should be returned

  Scenario: Updating a user's password
    Given a user with ID 1 and password "oldPassword"
    When the user's password is updated to "newPassword"
    Then the user's password should be updated in the database

  Scenario: Deleting a user
    Given a user with ID 1 exists
    When the user with ID 1 is deleted
    Then the user with ID 1 should no longer exist in the database
