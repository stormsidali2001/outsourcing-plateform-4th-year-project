package com.example.companymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class CompanyMicroserviceApplication  {

	public static void main(String[] args) {
		SpringApplication.run(CompanyMicroserviceApplication.class, args);
	}


}
