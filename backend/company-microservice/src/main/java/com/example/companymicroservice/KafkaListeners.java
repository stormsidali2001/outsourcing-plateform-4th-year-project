package com.example.companymicroservice;

import com.example.companymicroservice.dto.SignUpCompanyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(
            groupId = "group5",
            topics = "company-user-signed-up"
    )
    public void listener(ConsumerRecord data) throws JsonProcessingException {
      String message = (String) data.value();
      System.out.println("company-user-signed-up: message received ");
      try{
          SignUpCompanyDto company = objectMapper.readValue(message, SignUpCompanyDto.class);
          System.out.println("parse completed into a company object");


      }catch(Exception e){
          e.printStackTrace();
      }


    }
}
