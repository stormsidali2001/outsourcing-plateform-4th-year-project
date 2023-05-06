package com.example.workermicroservice.Entities.skill;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Builder @Data
public class Skill {
    @Id
    private String id;
    private String name;
    private String category;
}
