#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;

import java.util.ArrayList;
import java.util.List;


public class ErrorResponse {
	private final int httpStatus;
	private final String message;
	private final List<String> errors = new ArrayList<>();

	public ErrorResponse(int httpStatus, String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public ErrorResponse(int httpStatus, String message, String error) {
		this.httpStatus = httpStatus;
		this.message = message;
		errors.add(error);
	}

	public void addError(String error) {
		errors.add(error);
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public List<String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ErrorResponse [httpStatus=" + httpStatus + ", message=" + message + ", errors=" + errors + "]";
	}
}
