package com.example.workermicroservice;

import com.example.workermicroservice.Entities.category.Category;
import com.example.workermicroservice.Entities.skill.Skill;
import com.example.workermicroservice.dto.signupRequestDto.*;
import com.example.workermicroservice.repositpries.CategoryRepository;
import com.example.workermicroservice.repositpries.SkillRepository;
import com.example.workermicroservice.service.WorkerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class KafkaListeners {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private WorkerService workerService;



    @KafkaListener(
            topics = "worker-user-signed-up",
            groupId = "test"

    )
    void listener(ConsumerRecord data){
        System.out.println("...............................");
        String message = (String) data.value();
        try {
            SignUpRequestDto worker = objectMapper.readValue(message, SignUpRequestDto.class);
            // do something with the worker object
            System.out.println("Received data " + worker.toString());

            Set<String> categories = new HashSet<>();
            Set<String> skills = new HashSet<>();
            categories.add(worker.getCategory());
            worker.getSkills().stream().forEach((SkillDto s)->{
                categories.add(s.getCategory());
                skills.add(s.getName());
            });
            worker.getEducationDetails().stream().forEach((EducationDetailDto ed)->{
                ed.getSkills().stream().forEach((SkillDto s)->{
                    categories.add(s.getCategory());
                    skills.add(s.getName());
                });
            });
            worker.getCertifications().stream().forEach((CertificationDto ce)->{
                categories.add(ce.getSkill().getCategory());
                skills.add(ce.getSkill().getName());
            });

            worker.getPortfolioProjects().stream().forEach((PortfolioProjectDto pf)->{
                pf.getSkills().stream().forEach((SkillDto s)->{
                    categories.add(s.getCategory());
                    skills.add(s.getName());
                });
            });
            List<Category> categoriesDb = categoryRepository.findAllByName(categories.stream().toList());

            List<Skill> skillsDb = skillRepository.findAllByName(skills.stream().toList());
            workerService.signUpWorker(worker);
            System.out.println("data saved in db...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
