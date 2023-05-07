package com.example.workermicroservice;

import com.example.workermicroservice.dto.SignUpCompanyDto;
import com.example.workermicroservice.repositpries.CompanyRepository;
import com.example.workermicroservice.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class KafkaListeners {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;


    @KafkaListener(
            topics = "company-user-signed-up",
            groupId = "test"

    )
    void listener(ConsumerRecord data){
        System.out.println("...............................");
        String message = (String) data.value();
        try {
            SignUpCompanyDto company = objectMapper.readValue(message, SignUpCompanyDto.class);
            companyService.signupCompany(company);
            System.out.println("company saved into db");





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
