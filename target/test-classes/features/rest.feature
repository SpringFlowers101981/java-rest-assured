@rest
  Feature: Rest Api
    @rest1
    Scenario: Validation api
      Given I get candidate via api

    @rest2
    Scenario: REST API Candidates
      Given I validated CRUD operations for a candidates