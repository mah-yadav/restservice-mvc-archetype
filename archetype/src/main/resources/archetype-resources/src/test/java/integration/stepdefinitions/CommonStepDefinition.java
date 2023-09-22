#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion.VersionFlag;
import com.networknt.schema.ValidationMessage;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class CommonStepDefinition extends AbstractStepDefinition {
	private static final Logger LOG = LoggerFactory.getLogger(CommonStepDefinition.class);

	private static final String LOCAL_HOST = "http://localhost:";

	private static final String APPLICATION_CONTEXT = "/${artifactId}";

	@Autowired
	public CommonStepDefinition(TestRestTemplate testRestTemplate, @LocalServerPort int port) {
		super(testRestTemplate, port);
	}

	@After("@usehooks")
	public void tearDown() {
		LOG.info("Reseting scenario context.");
		getScenarioContext().reset();
	}

	@When("the request header has {string} {string}")
	public void the_request_header_has(String key, String value) {
		getScenarioContext().addHeader(key, value);
	}

	@When("the client make a {string} call to {string}")
	public void the_client_make_a_call_to(String requestMethod, String uri) {

		ResponseEntity<String> response = null;

		if (HttpMethod.POST.toString().equals(requestMethod)) {
			response = executePost(LOCAL_HOST + port + APPLICATION_CONTEXT + uri,
					getScenarioContext().getPayload(), getScenarioContext().getHeaders());
		} else if (HttpMethod.GET.toString().equals(requestMethod)) {
			response = executeGet(LOCAL_HOST + port + APPLICATION_CONTEXT + uri,
					getScenarioContext().getHeaders(), getScenarioContext().getQueryParams());
		} else if (HttpMethod.DELETE.toString().equals(requestMethod)) {
			response = executeDelete(LOCAL_HOST + port + APPLICATION_CONTEXT + uri,
					getScenarioContext().getHeaders());
		}

		getScenarioContext().setResponse(response);
	}

	@When("the request body does not contain {string}")
	public void the_request_body_doesn_t_contains(String key) {
		getScenarioContext().removeFromPayload(key);
	}

	@When("the client prepares another request")
	public void the_client_prepares_another_request() {
		getScenarioContext().reset();
	}

	@Then("the client receives status code {int} in response")
	public void the_client_receives_status_code_in_response(Integer statusCode) {
		ResponseEntity<String> response = getScenarioContext().getResponse();
		assertNotNull(response, "Response entity is not present.");
		assertEquals(statusCode, response.getStatusCode().value());
	}

	@Then("the client receives message {string}")
	public void the_client_receives_message(String message) {
		ResponseEntity<String> response = getScenarioContext().getResponse();
		String responseBody = response.getBody();
		assertNotNull("Response body is not present.", responseBody);

		if (HttpStatus.OK.equals(response.getStatusCode()) || HttpStatus.CREATED.equals(response.getStatusCode())) {
			assertSuccessMessage(responseBody, message);
		} else {
			assertFailureMessage(responseBody, message);
		}
	}

	@Then("the client receives response as {string}")
	public void the_client_receives_response_as(String expectedResponseSchema) {

		ResponseEntity<String> response = getScenarioContext().getResponse();
		String responseBody = response.getBody();
		JsonNode root = convertStringToJson(responseBody);

		JsonSchemaFactory factory = JsonSchemaFactory.getInstance(VersionFlag.V4);
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("schemas/" + expectedResponseSchema + ".json");
		JsonSchema jsonSchema = factory.getSchema(inputStream);

		Set<ValidationMessage> errors = jsonSchema.validate(root);

		assertEquals(0, errors.size());
	}

	@Then("the client receives {int} audit data in response")
	public void the_client_receives_audit_data_in_response(Integer count) {
		ResponseEntity<String> response = getScenarioContext().getResponse();

		String responseBody = response.getBody();
		assertNotNull("Response body is not present.", responseBody);

		JsonNode root = convertStringToJson(responseBody);
		JsonNode data = root.get("data");
		JsonNode auditData = data.get("auditdata");
		Iterator<JsonNode> iterator = auditData.iterator();

		int actualCount = 0;
		while (iterator.hasNext()) {
			iterator.next();
			actualCount++;
		}

		LOG.info("${model} actual count: {}", actualCount);

		assertEquals(count, actualCount);
	}

	private void assertSuccessMessage(String responseBody, String expectedMessage) {
		JsonNode root = convertStringToJson(responseBody);
		JsonNode messageNode = root.get("message");
		String successMessage = messageNode.asText();

		assertEquals(expectedMessage, successMessage);
	}

	private void assertFailureMessage(String responseBody, String expectedMessage) {
		JsonNode root = convertStringToJson(responseBody);
		JsonNode errorsNode = root.get("errors");

		Iterator<JsonNode> iterator = errorsNode.iterator();

		boolean foundExpectedErrorMessage = false;

		while (iterator.hasNext()) {
			JsonNode errorNode = iterator.next();
			if (expectedMessage.equals(errorNode.asText())) {
				foundExpectedErrorMessage = true;
			}
		}
		assertTrue(foundExpectedErrorMessage);
	}
}
