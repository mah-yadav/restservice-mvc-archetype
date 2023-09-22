#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;


public class ${project-name}ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ${project-name}ConflictException() {
		super();
	}

	public ${project-name}ConflictException(String message) {
		super(message);
	}

	public ${project-name}ConflictException(Throwable cause) {
		super(cause);
	}

	public ${project-name}ConflictException(String message, Throwable cause) {
		super(message, cause);
	}
}
