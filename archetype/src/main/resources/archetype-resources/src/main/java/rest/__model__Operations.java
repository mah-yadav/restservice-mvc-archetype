#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ${package}.utils.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@RequestMapping("/${resource}")
@SecurityScheme(name = "security_auth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "https://iam-devstable.cmp.barco.cloud/auth/realms/EDP/protocol/openid-connect/token", scopes = {
		@OAuthScope(name = "cmp.${project-name}.read", description = "cmp.${project-name}.read")

})))

public interface ${model}Operations {

	@Operation(summary = "Get ${model}.", tags = "${model}", security = {
			@SecurityRequirement(name = "security_auth", scopes = "cmp.${project-name}.read") }, responses = {
					@ApiResponse(responseCode = "200", description = "OK", content = {
							@Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)) }),
					@ApiResponse(responseCode = "401", description = "Unauthorized", content = {
							@Content(mediaType = "text/html", schema = @Schema(type = "string"), examples = {
									@ExampleObject(summary = "HTML response for unauthorized access", value = "<html>${symbol_escape}n<head><title>401 Authorization Required</title></head>${symbol_escape}n"
											+ "<body>${symbol_escape}n<center><h1>401 Authorization Required</h1></center>${symbol_escape}n"
											+ "<hr><center>openresty</center>${symbol_escape}n" + "</body>${symbol_escape}n" + "</html>") }) }) })

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuccessResponse> get${model}(
			@RequestParam(required = false) Map<String, String> queryParams);
}
