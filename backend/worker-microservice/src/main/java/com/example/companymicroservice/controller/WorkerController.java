package com.example.companymicroservice.controller;

import com.example.companymicroservice.Entities.skill.Skill;
import com.example.companymicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.companymicroservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @GetMapping("skills")
    public List<Skill> getSkills(){
        return this.workerService.getSkills();
    }

}
