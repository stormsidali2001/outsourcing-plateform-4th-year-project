package com.example.workermicroservice;

import com.example.workermicroservice.dto.signupRequestDto.SignUpRequestDto;
import com.example.workermicroservice.service.WorkerService;
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

    @Autowired
    private WorkerService workerService;

    @KafkaListener(
            topics = "worker-user-signed-up",
            groupId = "test"

    )
    void listener(ConsumerRecord data){
        System.out.println("...............................");
        String message = (String) data.value();
        try {
            SignUpRequestDto worker = objectMapper.readValue(message, SignUpRequestDto.class);
            // do something with the worker object
            System.out.println("Received data " + worker.toString());
            workerService.signUpWorker(worker);
            System.out.println("data saved in db...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
