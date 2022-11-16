package io.fana.cruel.app.config

import io.fana.cruel.app.security.LoginUserAuthHeader
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun api(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("cruel api")
        .pathsToMatch("/**")
        .build()

    @Bean
    fun openApi(): OpenAPI = OpenAPI()
        .info(
            Info().title("Cruel API")
                .description("Cruel API Docs")
                .version("1.0")
        )
        .components(
            Components()
                .addParameters(
                    LoginUserAuthHeader.USER_ID.name,
                    Parameter()
                        .`in`("header")
                        .required(true)
                        .description(LoginUserAuthHeader.USER_ID.name)
                        .name(LoginUserAuthHeader.USER_ID.name)
                )
                .addParameters(
                    LoginUserAuthHeader.USER_ROLE.name,
                    Parameter()
                        .`in`("header")
                        .required(true)
                        .description(LoginUserAuthHeader.USER_ROLE.name)
                        .name(LoginUserAuthHeader.USER_ROLE.name)
                )
        )
}
