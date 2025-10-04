package com.recipe.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new io.swagger.v3.oas.models.info.Info()
                        .title("Recipe API")
                        .version("1.0")
                        .description("API documentation for the Recipe application")
        ).servers(
                java.util.List.of(
                        new io.swagger.v3.oas.models.servers.Server().url("http://localhost:8080").description("Local Server")
                )
        );
    }
}
