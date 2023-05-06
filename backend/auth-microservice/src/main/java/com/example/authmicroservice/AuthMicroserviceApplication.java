package com.example.authmicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class AuthMicroserviceApplication implements  CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}
