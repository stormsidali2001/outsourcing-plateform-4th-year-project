package com.example.authmicroservice.controller;

import com.example.authmicroservice.Service.UserCredentialsService;
import com.example.authmicroservice.dto.UserCredentialsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserCredentialsService userCredentialsService;

    @PostMapping("register")
    public String registerUser(@RequestBody UserCredentialsDto user){
        userCredentialsService.save(user);
        return "user registered succesfully";
    }

    @PostMapping("signin")
    public String signinUser(@RequestBody UserCredentialsDto user){
        Authentication authentication  = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if(!authentication.isAuthenticated()){
            throw new RuntimeException("invalide access");
        }
        return userCredentialsService.generateToken(user);
    }

}
