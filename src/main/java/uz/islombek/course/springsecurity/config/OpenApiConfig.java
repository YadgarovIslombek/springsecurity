package uz.islombek.course.springsecurity.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openApi(){
        Components components = new Components();
        return new OpenAPI().components(components)
                .info(new Info()
                        .title("First Security and swagger app")
                        .version("1.0.0")
                        .description("Bu ilovada ksaljdklasjdklasj"));
    }

}
