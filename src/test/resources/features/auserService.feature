Feature: User Service Feature

  Scenario: Creating a user
    Given a user with ID 1 with first name "John" and last name "Doe"
    When the user is created
    Then the user should be persisted with a generated username

  Scenario: Getting all users
    When all users are requested
    Then all users should be returned

  Scenario: Getting a user by ID
    When a user with ID 1 is requested
    Then the user with ID 1 should be returned

  Scenario: Updating a user
    When the user's first name is updated to "Johnny" and last name is updated to "Doey" and role is updated to "USER"
    Then the user's first name ans last name should be updated in the database

  Scenario: Updating a user's password
    When the user's password is updated to "P@ssw0rd" with ID 1
    Then the user's password should be updated in the database


  Scenario: Activating and deactivating a user
    When the user with username "John.Doe" is activated
    Then the user with username "John.Doe" should be active in database



