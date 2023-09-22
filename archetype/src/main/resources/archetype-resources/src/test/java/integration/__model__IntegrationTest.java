#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.integration;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.junit.platform.engine.Constants;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("${packageInPathFormat}/integration")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "${package}.integration.stepdefinitions")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber-reports/cucumber-report.json, junit:target/surefire-reports/cucumber-report.xml, html:target/cucumber-reports/cucumber-report.html")

public class ${model}IntegrationTest {

}
