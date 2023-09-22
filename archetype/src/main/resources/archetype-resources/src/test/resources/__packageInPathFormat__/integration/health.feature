@health
Feature: Enable application monitoring

  Scenario: List all enabled monitoring end points
    Given the client makes GET call to "/status"
    When the client receives status code of 200 in response
    Then the client receives end point "self" in response
    And the client receives end point "health" in response
    And the client receives end point "health-path" in response

  Scenario: Check application liveness
    Given the client makes GET call to "/status/health/liveness"
    When the client receives status code of 200 in response
    Then the client receives "livenss" status "UP" in response
    
  Scenario: Check application readiness
    Given the client makes GET call to "/status/health/readiness"
    When the client receives status code of 200 in response
    Then the client receives "readiness" status "UP" in response
    
  Scenario: Check application health
    Given the client makes GET call to "/status/health"
    When the client receives status code of 200 in response
    Then the client receives "${project-name}" health status "UP" in response
    And the client receives "diskSpace" health status "UP" in response
    And the client receives "livenessState" health status "UP" in response
    And the client receives "mongo" health status "UP" in response
    And the client receives "ping" health status "UP" in response
    And the client receives "readinessState" health status "UP" in response
    And the client receives "health" status "UP" in response
