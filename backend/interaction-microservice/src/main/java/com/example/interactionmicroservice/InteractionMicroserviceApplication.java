package com.example.interactionmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

//@EnableWebMvc
public class InteractionMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InteractionMicroserviceApplication.class, args);
    }

}
