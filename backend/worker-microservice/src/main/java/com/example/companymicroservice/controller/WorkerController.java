package com.example.companymicroservice.controller;

import com.example.companymicroservice.Entities.skill.Skill;
import com.example.companymicroservice.Entities.worker.Worker;
import com.example.companymicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.companymicroservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @PostMapping("newWorker")
    public String newWorker(@RequestBody SignUpRequestDto worker){
        workerService.signUpWorker(worker);
        return "added";
    }

//    get one worker with his impressions
    @GetMapping("worker-interactions/{idWorker}")
    public Worker getWorkerWithImpressions( @PathVariable("idWorker") String idWorker) throws ChangeSetPersister.NotFoundException {
        return workerService.WorkerInteractions(idWorker);
    }
//get multiple workers with their impressions
    @GetMapping("/workers-interactions")
    public List<Worker> getWorkers(@RequestParam("workerIds") String workerIds) {
        return workerService.getWorkersWithInteractions(workerIds);
        
    }
}
