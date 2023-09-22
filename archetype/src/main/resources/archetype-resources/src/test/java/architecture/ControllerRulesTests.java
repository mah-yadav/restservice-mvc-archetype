#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = { "${package}.rest" })
public class ControllerRulesTests {

	@ArchTest
	public static final ArchRule operations_must_be_an_interface = 
			ArchRuleDefinition.classes().that().haveNameMatching(".*Operations").should().beInterfaces();
	
	@ArchTest
	public static final ArchRule controller_must_be_a_class = 
			ArchRuleDefinition.classes().that().haveNameMatching(".*Controller").should().beTopLevelClasses();
	
	@ArchTest
	public static final ArchRule controllers_must_implement_operations_interface = 
			ArchRuleDefinition.classes().that().areAnnotatedWith(RestController.class).should()
					.implement(checkWhetherInterfaceNameEndsWithOperations());
	
	
	@ArchTest
	public static final ArchRule methods_in_operations_interface_must_be_annotated_with_http_request_methods = 
			ArchRuleDefinition.methods().that().areAnnotatedWith(checkWhetherAnnotationIsForHTTPMethods()).should()
					.beDeclaredInClassesThat(checkWhetherInterfaceNameEndsWithOperations()).allowEmptyShould(true);
	
	@ArchTest
	public static final ArchRule methods_in_controllers_must_not_be_annotated_with_http_request_methods = 
			ArchRuleDefinition.noMethods().that().areAnnotatedWith(checkWhetherAnnotationIsForHTTPMethods()).should()
					.beDeclaredInClassesThat(checkWhetherClassIsAController()).allowEmptyShould(true);
	
	@ArchTest
	public static final ArchRule return_type_of_public_methods_in_controllers_should_be_responseEntity = 
			ArchRuleDefinition.methods().that().arePublic().and()
					.areDeclaredInClassesThat(checkWhetherClassIsAController()).should()
					.haveRawReturnType(ResponseEntity.class).allowEmptyShould(true);
	
	private static DescribedPredicate<JavaAnnotation> checkWhetherAnnotationIsForHTTPMethods() {
		return new DescribedPredicate<JavaAnnotation>("Check whether annotation is for http methods") {

			@Override
			public boolean test(JavaAnnotation annotation) {
				if (annotation.getClass().equals(GetMapping.class) || annotation.getClass().equals(PutMapping.class)
						|| annotation.getClass().equals(PostMapping.class)
						|| annotation.getClass().equals(DeleteMapping.class)) {
					return true;
				}
				return false;
			}
		};
	}
	
	private static DescribedPredicate<JavaClass> checkWhetherInterfaceNameEndsWithOperations() {
		return new DescribedPredicate<JavaClass>("Interface name must end with Operations.") {

			@Override
			public boolean test(JavaClass inputclass) {
				if (inputclass.isInterface() && inputclass.getName().endsWith("Operations")) {
					return true;
				}
				return false;
			}
		};
	}
	
	private static DescribedPredicate<JavaClass> checkWhetherClassIsAController() {
		return new DescribedPredicate<JavaClass>("Check whether class is a controller.") {

			@Override
			public boolean test(JavaClass inputclass) {
				if (!inputclass.isInterface() && inputclass.getName().endsWith("Controller")) {
					return true;
				}
				return false;
			}
		};
	}
}
