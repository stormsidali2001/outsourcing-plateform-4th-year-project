package com.example.authmicroservice.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
