Feature: Hello feature
  Scenario: User calls hello
    When user calls /hello
    Then the response is "Hello, Reactive World!"
