package com.example.authmicroservice;

import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.Repository.UserCredentialsRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @KafkaListener(
            groupId = "group2",
            topics = {"worker-unvalid","company-unvalid"}
    )
    public void listener(ConsumerRecord data){
        String userId = (String) data.value();
        userId= userId.substring(1,userId.length()-1); // removing the "<userId>"
        userCredentialsRepository.deleteById(userId);
        System.out.println("worker-unvalid event ---> user : "+userId+" deleted sucessfully");
    }
}
