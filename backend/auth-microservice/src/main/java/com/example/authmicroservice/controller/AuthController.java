package com.example.authmicroservice.controller;

import com.example.authmicroservice.Service.UserCredentialsService;
import com.example.authmicroservice.dto.RegisterUserDto;
import com.example.authmicroservice.dto.UserCredentialsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserCredentialsService userCredentialsService;

    @PostMapping("register")
    public String registerUser(@RequestBody RegisterUserDto user){
        userCredentialsService.save(user);
        return "user registered succesfully";
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
