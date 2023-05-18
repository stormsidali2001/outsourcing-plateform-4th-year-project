package com.example.workermicroservice.controller;

import com.example.workermicroservice.Entities.worker.Skill;
import com.example.workermicroservice.dtos.WorkerExistsResponseDto;
import com.example.workermicroservice.service.WorkerService;
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

    @GetMapping("workers-exist")
    public List<WorkerExistsResponseDto> getWorkersExist(@RequestParam("workerIds") List<String> workerIds){
        return this.workerService.getWorkersExist(workerIds);
    }


}
