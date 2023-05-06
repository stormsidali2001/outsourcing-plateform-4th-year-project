package com.example.authmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class AuthMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}


}
