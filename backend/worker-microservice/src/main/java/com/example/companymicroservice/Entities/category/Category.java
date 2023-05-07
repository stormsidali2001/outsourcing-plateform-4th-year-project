package com.example.companymicroservice.Entities.category;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder @Data
public class Category {
    @Id
    private String id;

    private String name;
}
