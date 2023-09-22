#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.usecase;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${package}.repository.${model}DataRepository;


@Service("${bean-prefix}Service")
public class ${model}Service implements ${model}UseCase {
	private static final Logger LOG = LoggerFactory.getLogger(${model}Service.class);

	private final ${model}DataRepository dataRepository;

	@Autowired
	public ${model}Service(${model}DataRepository dataRepository) {
		this.dataRepository = dataRepository;
	}

	@Override
	public void get${model}(Map<String, String> queryParams) {
		dataRepository.get${model}();
	}
}
