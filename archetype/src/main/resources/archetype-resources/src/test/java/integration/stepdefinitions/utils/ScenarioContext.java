#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.stepdefinitions.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public enum ScenarioContext {
	CONTEXT;

	private JsonNode payload;
	private ResponseEntity<String> response;
	private Map<String, String> headers = new HashMap<>();
	private Map<String, Object> queryParams = new HashMap<>();

	public JsonNode getPayload() {
		return payload;
	}

	public void setPayload(JsonNode payload) {
		this.payload = payload;
	}

	public ResponseEntity<String> getResponse() {
		return response;
	}

	public void setResponse(ResponseEntity<String> response) {
		this.response = response;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void removeHeader(String key) {
		headers.remove(key);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void putInPayload(String Key, String Value) {
		((ObjectNode) payload).put(Key, Value);
	}

	public void putInPayload(String Key, Boolean Value) {
		((ObjectNode) payload).put(Key, Value);
	}

	public void removeFromPayload(String Key) {
		((ObjectNode) payload).remove(Key);
	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void addQueryParams(String name, Object value) {
		queryParams.put(name, value);
	}


	public void reset() {
		payload = null;
		response = null;
		headers.clear();
		queryParams.clear();
	}
}
