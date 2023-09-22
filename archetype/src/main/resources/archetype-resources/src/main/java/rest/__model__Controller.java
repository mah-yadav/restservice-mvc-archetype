#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.usecase.${model}UseCase;
import ${package}.utils.SuccessResponse;


@RestController
@RequestMapping("/api/v1/${resource}")
public class ${model}Controller implements ${model}Operations {
	private static final Logger LOG = LoggerFactory.getLogger(${model}Controller.class);

	private final ${model}UseCase useCase;

	@Autowired
	public ${model}Controller(@Qualifier("${bean-prefix}Service") ${model}UseCase useCase) {
		this.useCase = useCase;
	}

	@Override
	public ResponseEntity<SuccessResponse> get${model}(Map<String, String> queryParams) {
		LOG.info("Request received for get ${model}.");

		useCase.get${model}(queryParams);

		SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK.value(),
				"${model} fetched successfully.");

		if (LOG.isDebugEnabled()) {
			LOG.debug("Response having audit data: {}", successResponse.toString());
		}

		return new ResponseEntity<>(successResponse, new HttpHeaders(), HttpStatus.OK);
	}
}
