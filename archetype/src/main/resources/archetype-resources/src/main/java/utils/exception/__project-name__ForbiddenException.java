#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;


public class ${project-name}ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ${project-name}ForbiddenException() {
		super();
	}

	public ${project-name}ForbiddenException(String message) {
		super(message);
	}

	public ${project-name}ForbiddenException(Throwable cause) {
		super(cause);
	}

	public ${project-name}ForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}
}
