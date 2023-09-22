#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.usecase;

import java.util.Map;


public interface ${model}UseCase {

	public void get${model}(Map<String, String> queryParams);
}
