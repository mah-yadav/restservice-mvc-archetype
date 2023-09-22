#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.GeneralCodingRules;

@AnalyzeClasses(packages = { "${package}" })
public class CodingRulesTest {

	@ArchTest
	private final ArchRule no_generic_exceptions = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

	@ArchTest
	private final ArchRule no_java_util_logging = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

	@ArchTest
	private final ArchRule loggers_should_be_private_static_final = ArchRuleDefinition.fields().that()
			.haveRawType(Logger.class).should().bePrivate().andShould().beStatic().andShould().beFinal()
			.because("This has been agreed.");

	@ArchTest
	private final ArchRule no_jodatime = GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;

	@ArchTest
	private final ArchRule no_field_injection = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

	@ArchTest
	private final ArchRule no_field_should_be_autowired = ArchRuleDefinition.noFields().should()
			.beAnnotatedWith(Autowired.class);
	
	@ArchTest
	private final ArchRule no_system_out_and_system_error_should_be_used = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

	@ArchTest
	private final ArchRule no_setter_should_be_autowired = ArchRuleDefinition.noMethods()
			.should(noMethodShouldBeAutowired());

	private ArchCondition<JavaMethod> noMethodShouldBeAutowired() {
		return new ArchCondition<JavaMethod>("Check method is not constructor and is autowired") {

			@Override
			public void check(JavaMethod method, ConditionEvents events) {
				if (!method.isConstructor() && method.isAnnotatedWith(Autowired.class)) {
					events.add(new SimpleConditionEvent(method, true,
							method.getFullName() + "is not constructor and is autowired."));
				}
			}
		};
	}

}
