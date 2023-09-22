#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.multitenancy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ${package}.utils.exception.${project-name}ValidationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class ${project-name}RequestInterceptor implements HandlerInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger(${project-name}RequestInterceptor.class);

	private static final String CLIENT_ID = "x-auth-clientid";
	private static final String MSG_VALIDATE_CLIENT_AND_TENANT_IN_HEADER = "Validate clientId from header";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String clientId = request.getHeader(CLIENT_ID);

		LOG.info(MSG_VALIDATE_CLIENT_AND_TENANT_IN_HEADER);
		LOG.debug("ClientId: {}", clientId);

		validateClientId(clientId);

		ClientContext.setCurrentClient(clientId);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		ClientContext.clear();
	}

	private void validateClientId(String clientId) {
		if (StringUtils.isEmpty(clientId)) {
			throw new ${project-name}ValidationException("Bad Request - ClientId is missing.");
		}
	}

}
