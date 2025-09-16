package com.financetracker.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    description = "JWT authentication. First obtain a token from /api/auth/login or /api/auth/register, then include it in the Authorization header as: Bearer <token>"
)
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server()
            .url("http://localhost:" + serverPort)
            .description("Local development server");

        Server productionServer = new Server()
            .url("https://your-production-domain.com")
            .description("Production server");

        Contact contact = new Contact()
            .name("Finance Tracker API Support")
            .email("support@financetracker.com")
            .url("https://financetracker.com/contact");

        License mitLicense = new License()
            .name("MIT License")
            .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
            .title("Finance Tracker API")
            .version("1.0.0")
            .description("A comprehensive REST API for personal finance management. Track income, expenses, and analyze your financial habits.")
            .contact(contact)
            .license(mitLicense)
            .termsOfService("https://financetracker.com/terms");
        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("bearerAuth");
        return new OpenAPI()
            .info(info)
            .servers(List.of(localServer, productionServer))/*.addSecurityItem(securityRequirement)*/;
    }
}
