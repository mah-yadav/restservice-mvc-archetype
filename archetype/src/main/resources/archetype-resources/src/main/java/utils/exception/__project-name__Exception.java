#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;


public class ${project-name}Exception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ${project-name}Exception() {
		super();
	}

	public ${project-name}Exception(String message) {
		super(message);
	}

	public ${project-name}Exception(Throwable cause) {
		super(cause);
	}

	public ${project-name}Exception(String message, Throwable cause) {
		super(message, cause);
	}
}
