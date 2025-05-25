Feature: Product API Operations

  Scenario: Add Product
    Given I am logged in with valid credentials
    When I add a product with valid data
    Then The product should be created successfully
    And I should be able to retrieve the product with correct details

  Scenario: Update Product
    Given I am logged in with valid credentials
    And A product exists
    When I update the product with new data
    Then The product should be updated successfully

  Scenario: Delete Product
    Given I am logged in with valid credentials
    And A product exists
    When I delete the product
    Then The product should not be retrievable
