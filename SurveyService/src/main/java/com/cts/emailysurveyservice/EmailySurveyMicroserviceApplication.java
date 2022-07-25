package com.cts.emailysurveyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/**
 * @author POD-4 This is the Main Application
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@OpenAPIDefinition
public class EmailySurveyMicroserviceApplication {

	/**
	 * Main Function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmailySurveyMicroserviceApplication.class, args);
	}

}
