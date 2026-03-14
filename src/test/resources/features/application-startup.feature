Feature: Blackjack application startup
  Scenario: The application reports that the project foundation is ready
    When the blackjack application starts
    Then the startup message is "Hello World!"
