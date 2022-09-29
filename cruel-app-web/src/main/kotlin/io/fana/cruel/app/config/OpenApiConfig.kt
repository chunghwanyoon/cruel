package io.fana.cruel.app.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
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
}

