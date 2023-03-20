package com.example.workermicroservice.controller;

import com.example.workermicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.workermicroservice.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("sign-up-request")
    public String signupRequest(SignUpRequestDto signUpRequestDto){
         workerService.signUpWorker(signUpRequestDto);
         return "done";
    }
}
