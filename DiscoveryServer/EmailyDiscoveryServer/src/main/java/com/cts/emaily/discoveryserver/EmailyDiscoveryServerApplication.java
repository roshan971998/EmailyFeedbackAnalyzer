package com.cts.emaily.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author POD-4 This is the Main Application
 */
@SpringBootApplication
@EnableEurekaServer
public class EmailyDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailyDiscoveryServerApplication.class, args);
	}

}
