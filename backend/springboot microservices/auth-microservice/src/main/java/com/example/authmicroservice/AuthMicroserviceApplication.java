package com.example.authmicroservice;

import com.example.authmicroservice.Repository.UserCredentialsRepository;
import com.example.authmicroservice.controller.AuthController;
import com.example.authmicroservice.dto.*;
import com.example.authmicroservice.types.CompanyType;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Date;
import java.util.List;

@SpringBootApplication
@EnableWebMvc

public class AuthMicroserviceApplication implements  CommandLineRunner{
	@Autowired
	private AuthController authController;

	@Autowired
	private UserCredentialsRepository userCredentialsRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthMicroserviceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		if(userCredentialsRepository.count() > 0) return;
		for(int i=0;i<10;i++){
			newWorker("assoulsidali"+i+"@gmail.com","sidali"+i,"assoul"+i);
			newCompany("Frigo dz"+i,"contact@frigodz.com");
		}

	}

	// utility functions
	public void newCompany(String name , String email) throws HttpException {
		authController.registerCompany(CompanySignUpDto.builder()
				.user(RegisterUserDto.builder()
						.email(email)
						.password("123456")
						.build())
				.company(
						CompanyDto.builder()
								.name(name)
								.size(255)
								.field("Computer Science")
								.website("http://localhost:8080")
								.socialMediaLinks(
										List.of(
												SocialMediaLinkDto.builder()
														.name("facebook")
														.url("http://localhost")
														.build()
										)
								)
								.type(CompanyType.COMPANY)
								.build()
				)
				.build());

	}
	public void newWorker(String email ,String firstName,String lastName) throws HttpException {
		authController.registerWorker(
				WorkerSignUpRequestDto.builder()
						.user(
								RegisterUserDto.builder()
										.email(email)
										.password("123456")
										.build()
						)
						.worker(WorkerDto.builder()
								.firstName(firstName)
								.lastName(lastName)
								.address(AddressDto.builder()
										.code_postal("06606")
										.addressDomissile("khlil amran")
										.numRue("06")
										.wilaya("Bejaia")
										.commune("tala hemza")
										.build())
								.category("Developement")
								.skills(
										List.of(
												SkillDto.builder()
														.name("React")
														.category("Developement")
														.build(),
												SkillDto.builder()
														.name("CSS")
														.category("Developement")
														.build()
										)
								)
								.publicPrice(60)
								.phoneNumber("+213663838507")
								.certifications(
										List.of(
												CertificationDto.builder()
														.companyName("mongodb")
														.issuedAt(Date.valueOf("2023-01-02"))
														.skill(
																SkillDto.builder()
																		.name("CSS")
																		.category("Developement")
																		.build()
														)
														.title("Mongodb Associate")
														.url("http://www.someRandomUrl.com")
														.build()
										)
								)
								.educationDetails(
										List.of(
												EducationDetailDto.builder()
														.description("study study study study study study study study study study diplome engineer")
														.startDate(Date.valueOf("2019-04-20"))
														.endDate(Date.valueOf("2024-04-20"))
														.field("Computer Science")
														.school("Esi sba")
														.location("La petite paris sidi bel abes")
														.build()

										)
								)
								.workExperiences(
										List.of(
												WorkExperienceDto.builder()
														.title("Full stack developer")
														.companyName("Data intuition")
														.startDate(Date.valueOf("2023-01-01"))
														.endDate(Date.valueOf("2023-03-01"))
														.description("description")
														.location("Bejaia")
														.type("Full stack developer")
														.build()
										)
								)
								.portfolioProjects(
										List.of(
												PortfolioProjectDto.builder()
														.projectGoal("project goals")
														.projectSolution("project solutions")
														.role("backend developer")
														.skills(List.of(
																SkillDto.builder()
																		.name("CSS")
																		.category("Developement")
																		.build()
														))
														.build()
										)
								)
								.build())


						.build()
		);

	}

}
