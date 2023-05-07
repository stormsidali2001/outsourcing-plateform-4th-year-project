package com.example.workermicroservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic getTopic(){
        return TopicBuilder
                .name("company-user-signed-up")
                .build();
    }
    @Bean
    public NewTopic getTopic1(){
        return TopicBuilder
                .name("company-unvalid")
                .build();
    }
}
