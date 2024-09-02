package com.godigit.bookmybook.config;

import com.godigit.bookmybook.util.TokenUtility;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public TokenUtility tokenUtility(){
        return new TokenUtility();
    }

    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Application")
                        .version("2.0.0")
                        .description("API documentation for Spring Boot Application"));
    }
}
