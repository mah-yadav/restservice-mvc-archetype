#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;


public class ${project-name}ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ${project-name}ResourceNotFoundException() {
		super();
	}

	public ${project-name}ResourceNotFoundException(String message) {
		super(message);
	}

	public ${project-name}ResourceNotFoundException(Throwable cause) {
		super(cause);
	}

	public ${project-name}ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
