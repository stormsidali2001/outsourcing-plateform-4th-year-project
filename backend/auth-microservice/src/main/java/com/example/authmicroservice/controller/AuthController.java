package com.example.authmicroservice.controller;

import com.example.authmicroservice.Service.UserCredentialsService;
import com.example.authmicroservice.dto.RegisterUserDto;
import com.example.authmicroservice.dto.UserCredentialsDto;
import com.example.authmicroservice.dto.WorkerDto;
import com.example.authmicroservice.dto.WorkerSignUpRequestDto;
import com.example.authmicroservice.types.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String registerWorker(@Valid  @RequestBody WorkerSignUpRequestDto data){
        String id = userCredentialsService.save(data.getUser(), Role.WORKER);
        WorkerDto worker = data.getWorker();
        worker.setUserId(id);
        kafkaTemplate.send("worker-user-signed-up",worker);
        return  "user registered succesfully";
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
    public Jws<Claims> validateToken(@RequestParam("token") String token ){
        return this.userCredentialsService.validateToken(token);
    }

    @GetMapping("users")
    public Object[] getUsers(){
        return this.userCredentialsService.getUsers();
    }

}
