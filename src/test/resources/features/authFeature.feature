Feature: Login Feature

  Scenario: Successful Login
    Given a user with username "testuser" and password "Test@123Test"
    When the user logs in
    Then a valid token is returned

  Scenario: Failed Login due to incorrect password
    Given a user with username "testuser" and incorrect password "InvalidPassword"
    When the user logs in
    Then no token is returned

  Scenario: Failed Login due to user being blocked
    Given a user with username "blockeduser" and password "Blocked@123"
    And the user is blocked
    When the user logs in
    Then no token is returned

  Scenario: Failed Login due to user not found
    Given a non-existing user with username "nonexistinguser" and password "NonExisting@123"
    When the user logs in
    Then no token is returned

