package com.spike.access_request_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class AccessRequestPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessRequestPlatformApplication.class, args);
	}

}
