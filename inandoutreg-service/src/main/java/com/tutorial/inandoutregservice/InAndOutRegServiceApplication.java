package com.tutorial.inandoutregservice;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class InAndOutRegServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InAndOutRegServiceApplication.class, args);
	}

}
