package com.tutorial.justificationsservice;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class JustificationsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustificationsServiceApplication.class, args);
	}

}
