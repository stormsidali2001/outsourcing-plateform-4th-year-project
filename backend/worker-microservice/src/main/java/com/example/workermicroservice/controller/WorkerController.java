package com.example.companymicroservice.controller;

import com.example.companymicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.companymicroservice.service.WorkerService;
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
