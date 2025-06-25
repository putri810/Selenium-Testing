Feature: Login Scenario

Background: User landend to login page
    Given User landing to logged ecommerce

Scenario: User input valid credentials
    When User input username "standard_user" and password "secret_sauce"
    Then User redirect to homepage

Scenario Outline: User input invalid username
    When User input username "<username>" and password "<password>"
    Then Verify error message "<username_error_message>" on username
    And Verify error message "<password_error_message>" on password

    Examples:
    |username                       | password        | username_error_message  | password_error_message |
    |standard_user                  |                 |                         | *Password is required  |
    |error_user                     | XBf@rWNvByn!#K8 | *Enter Valid Email      |                        |
    |                               | XBf@rWNvByn!#K8 | *Email is required      |                        |
    |                               |                 | *Email is required      | *Password is required  |