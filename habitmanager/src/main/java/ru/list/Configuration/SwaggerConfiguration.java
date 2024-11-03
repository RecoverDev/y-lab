package ru.list.Configuration;

import java.util.List;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfiguration {
    
    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                             .group("/")
                             .pathsToMatch("/**")
                             .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application-description}")String appDescription,
                                 @Value("${application-version}")String appVersion) {
        return new OpenAPI().info(new Info().title("Application API")
                                            .version(appVersion)
                                            .description(appDescription)
                                            .license(new License().name("Apache 2.0")
                                                                  .url("http://springdoc.org"))
                                            .contact(new Contact().name("recover")
                                                                  .email("recover@list.ru")))
                            .servers(List.of(new Server().url("http://localhost:8080")
                                                         .description("Habit Manager Service")));
    }
}
