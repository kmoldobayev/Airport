package kg.kuban.airport.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http://localhost:8080/swagger-ui/index.html#/
 */

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Документация по дипломному проекту АС Аэропорт")
                .version("1.0")
                .description("Документация по дипломному проекту АС Аэропорт");

        SecurityRequirement bearerTokenSecurityRequirement = new SecurityRequirement().addList("Bearer Authentication");

        Components jwtAuthorization = new Components().addSecuritySchemes("Bearer Authentication", this.createAPIKeyScheme());

        return new OpenAPI().info(info);
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}
