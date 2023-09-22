#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import java.util.HashMap;
import java.util.Map;



public class SuccessResponse {
	private final int httpStatus;
	private final String message;
	private final Map<String, Object> data = new HashMap<>();

	public SuccessResponse(int httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void addData(String name, Object value) {
		data.put(name, value);
	}

	@Override
	public String toString() {
		return "SuccessResponse [httpStatus=" + httpStatus + ", message=" + message + ", data=" + data + "]";
	}
}
