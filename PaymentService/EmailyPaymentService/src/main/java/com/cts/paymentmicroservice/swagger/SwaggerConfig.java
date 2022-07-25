package com.cts.paymentmicroservice.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Class for customizing swagger
 */

@Configuration
public class SwaggerConfig {
	
	
	/**
	 * Configure Swagger
	 * @return OpenApi which internally includes swagger
	 */
	   @Bean
	    public OpenAPI springShopOpenAPI() {
	        return new OpenAPI()
	                .info(new Info().title("Payment API")
	                .description("Emaily payment microservice")
	                .version("v0.0.1")
	                .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	                .externalDocs(new ExternalDocumentation()
	                .description("SpringShop Wiki Documentation")
	                .url("https://springshop.wiki.github.org/docs"));
	    }
}












