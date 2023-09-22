#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils.exception;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class ${project-name}AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(${project-name}AsyncExceptionHandler.class);

	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		LOG.error("Unexpected asynchronous exception at : " + method.getDeclaringClass().getName() + "."
				+ method.getName(), ex);
	}
}
