#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;


public class ${project-name}TestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ${project-name}TestException() {
		super();
	}

	public ${project-name}TestException(String message) {
		super(message);
	}

	public ${project-name}TestException(Throwable cause) {
		super(cause);
	}

	public ${project-name}TestException(String message, Throwable cause) {
		super(message, cause);
	}
}
