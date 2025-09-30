package com.tutorial.autorizationsservice;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class AutorizationsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorizationsServiceApplication.class, args);
	}

}
