#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration.stepdefinitions;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import ${package}.${project-name}Application;

import io.cucumber.spring.CucumberContextConfiguration;


@CucumberContextConfiguration
@SpringBootTest(classes = { ${project-name}Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ${project-name}CucmberContextConfiguration {
	
	static DockerImageName dockerImageName = DockerImageName.parse("mongo:4.4.13").asCompatibleSubstituteFor("mongo");

	@ServiceConnection
	static MongoDBContainer mongodb = new MongoDBContainer(dockerImageName);

	static {
		mongodb.start();
	}

}
