#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.stepdefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ${package}.integration.stepdefinitions.utils.ScenarioContext;
import ${package}.utils.${project-name}TestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class AbstractStepDefinition {
	private static final Logger LOG = LoggerFactory.getLogger(AbstractStepDefinition.class);

	protected int port;

	protected TestRestTemplate testRestTemplate;

	public AbstractStepDefinition(TestRestTemplate testRestTemplate, int port) {
		this.testRestTemplate = testRestTemplate;
		this.port = port;
	}

	ScenarioContext getScenarioContext() {
		return ScenarioContext.CONTEXT;
	}

	ResponseEntity<String> executeGet(String uri, Map<String, String> headers, Map<String, Object> queryParams) {
		HttpHeaders httpHeaders = new HttpHeaders();

		if (headers != null && !headers.isEmpty()) {
			headers.entrySet().stream().forEach((entry) -> {
				httpHeaders.addIfAbsent(entry.getKey(), entry.getValue());
			});
		}

		if (queryParams != null && !queryParams.isEmpty()) {
			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(uri);

			queryParams.entrySet().stream().forEach((entry) -> {
				uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
			});

			uri = uriComponentsBuilder.build().encode().toUriString();
		}

		LOG.info("Execute GET request: " + uri);

		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeaders),
				String.class);

		LOG.info("Response body: " + response.toString());

		return response;
	}

	ResponseEntity<String> executePost(String uri, JsonNode body, Map<String, String> headers) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(MediaType.APPLICATION_JSON);

		httpHeaders.setAccept(mediaTypes);

		if (headers != null && !headers.isEmpty()) {

			headers.entrySet().stream().forEach((entry) -> {
				httpHeaders.addIfAbsent(entry.getKey(), entry.getValue());
			});
		}

		ResponseEntity<String> response = null;

		HttpEntity<String> httpEntityRequest = new HttpEntity<String>(body.toString(), httpHeaders);
		LOG.info("Execute POST request: " + uri);
		LOG.info("Request header: " + httpEntityRequest.getHeaders().toString());
		LOG.info("Request body: " + httpEntityRequest.getBody());
		
		response = testRestTemplate.postForEntity(uri, httpEntityRequest, String.class);
		
		LOG.info("Response body: " + response.toString());

		return response;
	}

	ResponseEntity<String> executeDelete(String uri, Map<String, String> headers) {
		HttpHeaders httpHeaders = new HttpHeaders();

		if (headers != null && !headers.isEmpty()) {
			headers.entrySet().stream().forEach((entry) -> {
				httpHeaders.addIfAbsent(entry.getKey(), entry.getValue());
			});
		}

		LOG.info("Execute DELETE request: " + uri);
		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE,
				new HttpEntity<>(httpHeaders), String.class);

		if (response != null) {
			LOG.info("Response body: " + response.toString());
		}

		return response;
	}

	JsonNode convertStringToJson(String body) {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(body);
		} catch (JsonProcessingException e) {
			new ${project-name}TestException(e.getMessage(), e.getCause());
		}
		return root;
	}

	JsonNode convertObjectToJson(Object body) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.valueToTree(body);
	}
}
