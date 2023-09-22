#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import ${package}.${project-name}Application;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ${project-name}Application.class)
@Testcontainers
public class ${project-name}ContextTests {

	static DockerImageName dockerImageName = DockerImageName.parse("mongo:4.4.13").asCompatibleSubstituteFor("mongo");

	@ServiceConnection
	static MongoDBContainer mongodb = new MongoDBContainer(dockerImageName);

	static {
		mongodb.start();
	}

	@Test
	public void contextLoads() {
	}

}
