package com.example.workermicroservice.service;

import com.example.workermicroservice.Entities.worker.EducationDetail;
import com.example.workermicroservice.Entities.worker.Skill;
import com.example.workermicroservice.Entities.worker.Worker;
import com.example.workermicroservice.dto.signupRequestDto.EducationDetailDto;
import com.example.workermicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.workermicroservice.dto.signupRequestDto.SkillDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Service
public class WorkerService {

    public void signUpWorker(SignUpRequestDto sq){
        Worker worker = Worker.builder()
                .firstName(sq.getFirstName())
                .lastName(sq.getLastName())
                .educationDetails(sq.getEducationDetails().stream().map(this::mapEducationDetail).toList())
                .build();
    }

    public EducationDetail mapEducationDetail(EducationDetailDto ed){
        return EducationDetail.builder()
                .description(ed.getDescription())
                .field(ed.getField())
                .startDate(ed.getStartDate())
                .endDate(ed.getEndDate())
                .school(ed.getSchool())
                .location(ed.getLocation())
                .skills(ed.getSkills().stream().map(this::mapToSkill).toList())
                .build();
    }

    public Skill mapToSkill(SkillDto skillDto){
        return Skill.builder()
                .name(skillDto.getName())
                .category(skillDto.getCategory())
                .build();
    }
}
