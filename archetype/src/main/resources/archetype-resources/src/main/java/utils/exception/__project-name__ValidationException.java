#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;


public class ${project-name}ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ${project-name}ValidationException() {
		super();
	}

	public ${project-name}ValidationException(String message) {
		super(message);
	}

	public ${project-name}ValidationException(Throwable cause) {
		super(cause);
	}

	public ${project-name}ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
