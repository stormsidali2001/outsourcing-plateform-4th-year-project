package com.example.companymicroservice.Entities.worker;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Skill {
    private String name;
    private String category;
}
