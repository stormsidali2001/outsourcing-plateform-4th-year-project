package com.example.companymicroservice;

import com.example.companymicroservice.Entities.category.Category;
import com.example.companymicroservice.Entities.skill.Skill;
import com.example.companymicroservice.repositpries.CategoryRepository;
import com.example.companymicroservice.repositpries.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@SpringBootApplication
@EnableWebMvc
public class WorkerMicroserviceApplication implements CommandLineRunner {
	@Autowired
	private SkillRepository skillRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(WorkerMicroserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(categoryRepository.count() ==  0) {
			categoryRepository.saveAll(
					List.of(
							Category.builder()
									.name("Developement")
									.build(),
							Category.builder()
									.name("Graphic Design")
									.build(),
							Category.builder()
									.name("3d Modeling")
									.build(),
							Category.builder()
									.name("UI/UX Design")
									.build(),
							Category.builder()
									.name("Instructor")
									.build()

					)
			);

		}

		if(skillRepository.count() == 0){
			skillRepository.saveAll(
					List.of(
							Skill.builder()
									.name("React")
									.category("Developement")
									.build(),
							Skill.builder()
									.name("CSS")
									.category("Developement")
									.build(),
							Skill.builder()
									.name("Js")
									.category("Developement")
									.build(),
							Skill.builder()
									.name("Go")
									.category("Developement")
									.build(),
							Skill.builder()
									.name("Rust")
									.category("Developement")
									.build()
					)
			);
		}


	}
}
