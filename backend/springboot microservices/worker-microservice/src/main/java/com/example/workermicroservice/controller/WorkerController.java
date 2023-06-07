package com.example.workermicroservice.controller;

import com.example.workermicroservice.Entities.worker.Skill;
import com.example.workermicroservice.Entities.worker.Worker;
import com.example.workermicroservice.Projections.WorkerProjection;
import com.example.workermicroservice.dtos.PaginatedWorkerResponse;
import com.example.workermicroservice.dtos.WorkerExistsResponseDto;
import com.example.workermicroservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("")
    public List<PaginatedWorkerResponse> getAllWorkers( @RequestParam("page")  Integer page , @RequestParam("pageSize") Integer pageSize){
        return this.workerService.getAllWorkers(page,pageSize);
    }

    @PostMapping("{workerId}/approve")
    public ResponseEntity<String> approveWorker(@PathVariable("workerId") String workerId){
        return workerService.approveWorker(workerId);
    }

    @GetMapping("worker-exist")
    public boolean deosWorkerExist(@RequestParam("idWorker") String idWorker){
        return workerService.deosWorkerExist(idWorker);
    }
    @GetMapping("all-ids")
    public List<String> getWorkerIds(){
        return this.workerService.getWorkerIds();
    }
    @GetMapping("admin/workers")
    public List<WorkerProjection> getWorkers(){
        return this.workerService.getWorkers();
    }

    @GetMapping("worker/{idWorker}")
    public Worker getWorker(@PathVariable("idWorker") String idWorker){
        return workerService.WorkerWithNbrInteractions(idWorker);
    }

    @GetMapping("test-route")
    public String test(@RequestHeader(value = "X-userId", required = false) String userId) {
        if (userId != null) {
            return userId;
        } else {
            return "NO headers found";
        }
    }




}
