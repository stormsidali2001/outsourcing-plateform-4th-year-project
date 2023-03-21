package com.example.workermicroservice.controller;

import com.example.workermicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.workermicroservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("sign-up-request")
    public String signupRequest(@RequestBody  SignUpRequestDto signUpRequestDto){
         workerService.signUpWorker(signUpRequestDto);
         return "done";
    }
}
