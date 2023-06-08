package com.example.companymicroservice;

import com.example.companymicroservice.Entities.companyField.CompanyField;
import com.example.companymicroservice.repositpries.CompanyFieldRepository;
import com.example.companymicroservice.repositpries.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@SpringBootApplication
@EnableWebMvc
public class CompanyMicroserviceApplication implements CommandLineRunner {
	@Autowired
	private CompanyFieldRepository companyFieldRepository;

	@Autowired
	private CompanyRepository companyRepository;

	public static void main(String[] args) {
		SpringApplication.run(CompanyMicroserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.companyRepository.deleteAll();
		if(companyFieldRepository.count() > 0) return;
		companyFieldRepository.saveAll(
				List.of(
						CompanyField.builder()
								.name("Computer Science")
								.build(),
						CompanyField.builder()
								.name("Random hdra to fill the gaps")
								.build(),
						CompanyField.builder()
								.name("Random hdra to fill the gaps1")
								.build()
				)
		);
	}
}
