package com.example.companymicroservice.Entities.company;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(value = "cardItem") @Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardItem {

    @Id
    private String workerId;
    private String firstName;
    private String lastName;
    private float publicPrice;
    private int nbHours;
    private  String category;
    private List<Skill> skills=new ArrayList<>();;
}
