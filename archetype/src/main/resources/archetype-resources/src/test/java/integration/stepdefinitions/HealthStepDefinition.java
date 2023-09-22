#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * 
 * @author mahesh
 *
 *         HealthStepDefinition has implementation/definition of steps mentioned
 *         in health.feature file. Scope of this class to provide step
 *         definition for steps mentioned in health.feaure file.
 *         HealthStepDefinition extends AbstractStepDefinition to access generic
 *         and spring functionalities.
 *
 */

public class HealthStepDefinition extends AbstractStepDefinition {

	@Autowired
	public HealthStepDefinition(TestRestTemplate testRestTemplate, @LocalServerPort int port) {
		super(testRestTemplate, port);
	}

	private static final Logger LOG = LoggerFactory.getLogger(HealthStepDefinition.class);

	private static final String LOCAL_HOST = "http://localhost:";
	private static final String APPLICATION_CONTEXT = "/${artifactId}";

	private static final String STATUS = "status";
	private static final String LINKS = "_links";
	private static final String COMPONENTS = "components";

	@Given("the client makes GET call to {string}")
	public void the_client_makes_get_call_to(String uri) {
		Map<String, String> headers = new HashMap<>();

		Map<String, Object> queryParams = new HashMap<>();

		ResponseEntity<String> response = executeGet(LOCAL_HOST + port + APPLICATION_CONTEXT + uri, headers,
				queryParams);

		getScenarioContext().setResponse(response);
	}

	@When("the client receives status code of {int} in response")
	public void the_client_receives_status_code_in_response(Integer statusCode) {
		ResponseEntity<String> response = getScenarioContext().getResponse();

		assertNotNull(response);
		assertEquals(statusCode, response.getStatusCode().value());
	}

	@Then("the client receives end point {string} in response")
	public void the_client_receives_end_point_in_response(String endpoint)
			throws JSONException, JsonMappingException, JsonProcessingException {
		ResponseEntity<String> response = getScenarioContext().getResponse();

		String responseBody = response.getBody();
		assertNotNull("Response body is not present.", responseBody);

		JsonNode root = convertStringToJson(responseBody);

		JsonNode links = root.get(LINKS);
		assertNotNull(links, "_links are not present in response.");

		JsonNode endpointNode = links.get(endpoint);
		assertNotNull(endpointNode, "Health endpoint " + endpoint + " is not present in response.");

		JsonNode endpointUrl = endpointNode.get("href");
		assertNotNull("Health enpoint url for" + endpoint + "is not present.", endpointUrl.asText());

		LOG.info("Endpoint " + endpointUrl.asText() + " is active.");

	}

	@Then("the client receives {string} status {string} in response")
	public void the_client_receives_status_in_response(String endpoint, String status) {
		ResponseEntity<String> response = getScenarioContext().getResponse();

		String responseBody = response.getBody();
		assertNotNull("Response body is not present.", responseBody);

		JsonNode root = convertStringToJson(responseBody);
		JsonNode statusNode = root.get(STATUS);

		LOG.info(endpoint.toUpperCase() + " status is " + statusNode.asText());
		assertEquals(status, statusNode.asText());

	}

	@Then("the client receives {string} health status {string} in response")
	public void the_client_receives_health_status_in_response(String component, String status) {
		ResponseEntity<String> response = getScenarioContext().getResponse();

		String responseBody = response.getBody();
		assertNotNull("Response body is not present.", responseBody);

		JsonNode root = convertStringToJson(responseBody);
		JsonNode componentsNode = root.get(COMPONENTS);

		assertNotNull(componentsNode, "Components are not present.");

		JsonNode componentNode = componentsNode.get(component);
		assertNotNull(componentsNode, component + " are not present.");

		JsonNode statusNode = componentNode.get(STATUS);

		LOG.info(component.toUpperCase() + " status is " + statusNode.asText());
		assertEquals(status, statusNode.asText());
	}
}
