package kg.kuban.airport.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Документация по дипломному проекту АС Аэропорт")
                .version("1.0")
                .description("Документация по дипломному проекту АС Аэропорт");
        return new OpenAPI().info(info);
    }
}
