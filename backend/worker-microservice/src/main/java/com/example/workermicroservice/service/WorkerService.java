package com.example.workermicroservice.service;

import com.example.workermicroservice.Entities.worker.EducationDetail;
import com.example.workermicroservice.Entities.worker.Skill;
import com.example.workermicroservice.Entities.worker.Worker;
import com.example.workermicroservice.dto.signupRequestDto.EducationDetailDto;
import com.example.workermicroservice.dto.signupRequestDto.SignUpRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class WorkerService {

    public void signUpWorker(SignUpRequestDto sq){
        Worker worker = Worker.builder()
                .firstName(sq.getFirstName())
                .lastName(sq.getLastName())
                .educationDetails(sq.getEducationDetails())
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
                .skills()
                .build();
    }

    public Skill mapToSkill(S)
}
