package com.example.workermicroservice.Entities.worker;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Skill {
    private String name;
    private String category;
}
