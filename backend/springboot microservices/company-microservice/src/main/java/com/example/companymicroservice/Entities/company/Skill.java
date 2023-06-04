package com.example.companymicroservice.Entities.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "skill") @Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    private String name;
    private String category;
}
