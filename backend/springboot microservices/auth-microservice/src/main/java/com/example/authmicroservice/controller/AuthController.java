package com.example.authmicroservice.controller;

import com.example.authmicroservice.Service.UserCredentialsService;
import com.example.authmicroservice.dto.*;
import com.example.authmicroservice.exception.BadRequestException;
import com.example.authmicroservice.types.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.validation.Valid;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    KafkaTemplate<Object,Object> kafkaTemplate;


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserCredentialsService userCredentialsService;


//    @PostMapping("register")
//    public String registerUser(@RequestBody RegisterUserDto user){
//        userCredentialsService.save(user);
//        kafkaTemplate.send("worker-user-signed-up","hiiiiiiiiiiiiiiiiiiii worker");
//        return "user registered succesfully";
//    }

    @PostMapping("registration/worker")
    public ResponseEntity<String> registerWorker( @RequestBody @Valid WorkerSignUpRequestDto data) throws HttpException {
        try{
            String id = userCredentialsService.save(data.getUser(), Role.WORKER);
            WorkerDto worker = data.getWorker();
            worker.setUserId(id);
            kafkaTemplate.send("worker-user-signed-up",worker);
            return  ResponseEntity.ok("worker registered succesfully");

        }catch(BadRequestException e){
            return ResponseEntity.badRequest().body("User email already exist");
        }

    }
    @PostMapping("registration/company")
    public ResponseEntity<String> registerCompany( @RequestBody @Valid CompanySignUpDto data) throws HttpException {
        try{
            String id = userCredentialsService.save(data.getUser(), Role.COMPANY);
            CompanyDto company = data.getCompany();
            company.setUserId(id);
            kafkaTemplate.send("company-user-signed-up",company);
            return  ResponseEntity.ok("worker registered succesfully");

        }catch(BadRequestException e){
            return ResponseEntity.badRequest().body("User email already exist");
        }


    }

    @PostMapping("signin")
    public String signinUser(@RequestBody UserCredentialsDto user){
        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new RuntimeException("invalide access");
        }
        return userCredentialsService.generateToken(user);
    }

    @GetMapping("validate-token")
    public ValidateTokenResponse validateToken(@RequestParam("token") String token ){
        return    this.userCredentialsService.validateToken(token);
    }

    @GetMapping("users")
    public Object[] getUsers(){
        return this.userCredentialsService.getUsers();
    }

}
