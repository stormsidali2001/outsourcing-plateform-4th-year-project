package com.example.companymicroservice;

import com.example.companymicroservice.Entities.companyField.CompanyField;
import com.example.companymicroservice.dto.SignUpCompanyDto;
import com.example.companymicroservice.repositpries.CompanyFieldRepository;
import com.example.companymicroservice.service.CompanyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaListeners {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaTemplate<Object,Object> kafkaTemplate;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyFieldRepository companyFieldRepository;

    @KafkaListener(
            groupId = "group5",
            topics = "company-user-signed-up"
    )
    public void listener(ConsumerRecord data) throws JsonProcessingException {
      String message = (String) data.value();
      System.out.println("company-user-signed-up: message received "+message);
      try{
          SignUpCompanyDto company = objectMapper.readValue(message, SignUpCompanyDto.class);
          System.out.println("parse completed into a company object "+company.toString());
          try{
              companyService.signupCompany(company);

              Optional<CompanyField> optC =  companyFieldRepository.findByName(company.getField());
              if(optC.isEmpty()){
                  System.out.println("company-unvalid ");
                  kafkaTemplate.send("company-unvalid",company.getUserId());
                  return;
              }

          }catch (Exception e){
              kafkaTemplate.send("company-unvalid",company.getUserId());
              e.printStackTrace();
          }


      }catch(Exception e){
          e.printStackTrace();

      }




    }
}
