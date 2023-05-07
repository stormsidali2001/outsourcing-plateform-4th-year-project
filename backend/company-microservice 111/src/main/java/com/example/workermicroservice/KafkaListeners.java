package com.example.companymicroservice;

import com.example.companymicroservice.dto.SignUpCompanyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private CompanyService companyService;

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
//            companyService.signupCompany(company);
            System.out.println("company saved into db");





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
