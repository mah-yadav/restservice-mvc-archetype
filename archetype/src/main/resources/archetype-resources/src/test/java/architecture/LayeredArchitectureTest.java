#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = { "${package}" })
public class LayeredArchitectureTest {

	@ArchTest
	public static final ArchRule layer_dependencies_are_respected = Architectures.layeredArchitecture()
			.consideringAllDependencies()

			.layer("${model}Rest").definedBy("${package}.rest")
			.layer("${model}Usecase").definedBy("${package}.usecase")
			.layer("${model}Repository").definedBy("${package}.repository")
			
			.layer("${model}Multitenancy").definedBy("${package}.multitenancy")

			.whereLayer("${model}Rest").mayNotBeAccessedByAnyLayer()
			.whereLayer("${model}Usecase")
			.mayOnlyBeAccessedByLayers("${model}Rest")
			.whereLayer("${model}Repository").mayOnlyBeAccessedByLayers("${model}Usecase");
}
