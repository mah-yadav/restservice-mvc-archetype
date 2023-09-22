#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = { "${package}" })
public class ClassDependencyRulesTest {

	@ArchTest
	public static final ArchRule utils_should_not_access_rest_or_usecase_or_repository = ArchRuleDefinition.noClasses()
			.that().resideInAPackage("..utils..").should().accessClassesThat()
			.resideInAnyPackage("${package}.rest", "${package}.usecase",
					"${package}.repository");

}
