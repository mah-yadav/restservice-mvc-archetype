#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture;

import org.springframework.stereotype.Service;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = { "${package}.usecase" })
public class ServiceRulesTests {
	
	@ArchTest
	public static final ArchRule usecase_must_be_an_interface = 
			ArchRuleDefinition.classes().that().haveNameMatching(".*UseCase").should().beInterfaces();
	
	@ArchTest
	public static final ArchRule service_must_be_a_class = 
			ArchRuleDefinition.classes().that().haveNameMatching(".*Service").should().beTopLevelClasses();

	@ArchTest
	public static final ArchRule services_must_implement_usecase_interface = 
			ArchRuleDefinition.classes().that().areAnnotatedWith(Service.class).should()
					.implement(checkWhetherInterfaceNameEndsWithUseCase());
	
	
	
	private static DescribedPredicate<JavaClass> checkWhetherInterfaceNameEndsWithUseCase() {
		return new DescribedPredicate<JavaClass>("Interface name must end with UseCase.") {

			@Override
			public boolean test(JavaClass inputclass) {
				if (inputclass.isInterface() && inputclass.getName().endsWith("UseCase")) {
					return true;
				}
				return false;
			}
		};
	}
}
