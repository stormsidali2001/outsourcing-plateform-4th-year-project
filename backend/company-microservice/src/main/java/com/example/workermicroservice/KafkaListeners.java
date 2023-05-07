package com.example.workermicroservice;

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
    private KafkaTemplate<Object,Object> kafkaTemplate;


    @KafkaListener(
            topics = "worker-user-signed-up",
            groupId = "test"

    )
    void listener(ConsumerRecord data){
        System.out.println("...............................");
        String message = (String) data.value();
        try {





        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
