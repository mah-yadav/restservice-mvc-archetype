#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.status;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;


@Component
public class ${project-name}HealthIndicator implements HealthIndicator {
	private static final String APP_KEY = "${project-name}";

	@Override
	public Health health() {
		if (!isServiceRunning()) {
			return Health.down().withDetail(APP_KEY, "Not Available").build();
		}
		return Health.up().withDetail(APP_KEY, "Available").build();
	}

	private boolean isServiceRunning() {
		return true;
	}
}
