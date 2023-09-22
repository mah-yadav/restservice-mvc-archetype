#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ${package}.multitenancy.${project-name}RequestInterceptor;


@Configuration
public class ${project-name}WebConfigurationAdaptor implements WebMvcConfigurer {

	private ${project-name}RequestInterceptor requestInterceptor;

	@Autowired
	public ${project-name}WebConfigurationAdaptor(${project-name}RequestInterceptor requestInterceptor) {
		this.requestInterceptor = requestInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor).addPathPatterns("/**/${resource}/**");
	}
}
