package com.md.login.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringFoxConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info( new Info()
                        .title("Login Microservice")
                        .description("Login microservice corresponding to the MobyWallet.")
                        .version("v1.0.0")
                );
    }
}
