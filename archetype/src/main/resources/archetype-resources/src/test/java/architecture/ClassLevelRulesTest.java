#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = { "${package}" })
public class ClassLevelRulesTest {

	@ArchTest
	public static final ArchRule repositories_must_reside_in_a_repository_package = ArchRuleDefinition.classes().that()
			.haveNameMatching(".*Repository").should()
			.resideInAnyPackage("${package}.repository")
			.as("Repositories should reside in a package '${package}.repository'");

	@ArchTest
	public static final ArchRule class_annotated_with_repository_must_reside_in_repository_package = ArchRuleDefinition
			.classes().that().areAnnotatedWith(Repository.class).should()
			.resideInAnyPackage("${package}.repository")
			.as("Classes annotated with @Repository should reside in a package '${package}.repository'")
			.allowEmptyShould(true);

	@ArchTest
	public static final ArchRule services_must_reside_in_a_usecase_package = ArchRuleDefinition.classes().that()
			.haveNameMatching(".*Service").should()
			.resideInAnyPackage("${package}.usecase")
			.as("Service should reside in a package '${package}.usecase'");

	@ArchTest
	public static final ArchRule class_annotated_with_service_must_reside_in_usecase_package = ArchRuleDefinition
			.classes().that().areAnnotatedWith(Service.class).should()
			.resideInAnyPackage("${package}.usecase")
			.as("Classes annotated with @Service should reside in a package '${package}.usecase'");

	@ArchTest
	public static final ArchRule controllers_must_reside_in_a_rest_package = ArchRuleDefinition.classes().that()
			.haveNameMatching(".*Controller").should()
			.resideInAnyPackage("${package}.rest")
			.as("Controllers should reside in a package '${package}.rest'");

	@ArchTest
	public static final ArchRule class_annotated_with_restcontroller_must_reside_in_rest_package = ArchRuleDefinition
			.classes().that().areAnnotatedWith(RestController.class).should()
			.resideInAnyPackage("${package}.rest")
			.as("Classes annotated with @RestController should reside in a package '${package}.rest'");

}
