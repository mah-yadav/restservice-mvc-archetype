#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.info.BuildProperties;

@Configuration
public class SwaggerConfiguration {

    private BuildProperties buildInfo;
    private ${project-name}ServerProperties serverProperties;

    @Autowired
    public SwaggerConfiguration(BuildProperties buildInfo, ${project-name}ServerProperties serverProperties) {
        this.buildInfo = buildInfo;
        this.serverProperties = serverProperties;
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url(serverProperties.getContextPath()))
                .info(new Info().title("${description}").version(buildInfo.getVersion())
                        .description("${description} REST API interfaces"));
    }
}
