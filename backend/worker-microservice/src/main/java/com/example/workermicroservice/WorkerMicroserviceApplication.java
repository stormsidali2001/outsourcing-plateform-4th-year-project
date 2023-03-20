package com.example.workermicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class WorkerMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkerMicroserviceApplication.class, args);
	}

}
