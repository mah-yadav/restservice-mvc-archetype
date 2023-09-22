#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ${project-name}ExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(${project-name}ExceptionHandler.class);

	private static final String LOG_ERROR_MSG_INITIAL = "Error response :";
	private static final String BAD_REQUEST_GENERIC_MSG = "Invalid request";
	private static final String CONFLICT_GENERIC_MSG = "Resource already exists";
	private static final String NOT_FOUND_GENERIC_MSG = "Resource Not found";
	private static final String INTERNAL_ERROR_GENERIC_MSG = "Something goes wrong";
	private static final String FORBIDDEN = "Action not allowed";

	@ExceptionHandler(${project-name}ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // Added for swagger documentation
	public ResponseEntity<ErrorResponse> handle${project-name}ValidationException(${project-name}ValidationException ex,
			WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_GENERIC_MSG,
				ex.getMessage());

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(${project-name}ConflictException.class)
	public ResponseEntity<ErrorResponse> handle${project-name}ConflictException(${project-name}ConflictException ex,
			WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), CONFLICT_GENERIC_MSG,
				ex.getMessage());

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(${project-name}ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handle${project-name}ResourceNotFoundException(
			${project-name}ResourceNotFoundException ex, WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), NOT_FOUND_GENERIC_MSG,
				ex.getMessage());

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(${project-name}ForbiddenException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN) // Added for swagger documentation
	public ResponseEntity<ErrorResponse> handle${project-name}ForbiddenException(${project-name}ForbiddenException ex,
			WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), FORBIDDEN, ex.getMessage());

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(${project-name}Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Added for swagger documentation
	public ResponseEntity<ErrorResponse> handle${project-name}Exception(${project-name}Exception ex, WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				INTERNAL_ERROR_GENERIC_MSG, ex.getMessage());

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_GENERIC_MSG);

		ex.getBindingResult().getFieldErrors().forEach(error -> errorResponse.addError(error.getDefaultMessage()));

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // Added for swagger documentation
	public ResponseEntity<ErrorResponse> handleUnhandledMaxUploadSizeExceededException(
			MaxUploadSizeExceededException ex, WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), BAD_REQUEST_GENERIC_MSG,
				ex.getMessage());

		LOG.error(BAD_REQUEST_GENERIC_MSG + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Added for swagger documentation
	public ResponseEntity<ErrorResponse> handleUnhandledException(Exception ex, WebRequest request) {

		LOG.error(ex.getMessage(), ex.getCause());

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				INTERNAL_ERROR_GENERIC_MSG, ex.getMessage());

		LOG.error(LOG_ERROR_MSG_INITIAL + errorResponse.toString());

		return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
