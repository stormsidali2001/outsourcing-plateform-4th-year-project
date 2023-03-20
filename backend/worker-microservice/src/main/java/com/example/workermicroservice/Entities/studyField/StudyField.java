package com.example.workermicroservice.Entities.studyField;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder @Data
public class StudyField {
    @Id
    private String id;

    private String name;

}
