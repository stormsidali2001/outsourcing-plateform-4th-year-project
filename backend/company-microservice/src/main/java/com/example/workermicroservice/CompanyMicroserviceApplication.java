package com.example.workermicroservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@SpringBootApplication
@EnableWebMvc
public class CompanyMicroserviceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CompanyMicroserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
