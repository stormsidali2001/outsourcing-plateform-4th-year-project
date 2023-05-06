package com.example.workermicroservice;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics = "hi",
            groupId = "test"

    )
    void listener(Object data){
        System.out.println("...............................");
        System.out.println("received data "+data.toString());
    }
}
