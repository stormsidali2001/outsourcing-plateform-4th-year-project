package com.example.companymicroservice.Entities.company;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(value = "card")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {


@Id
private String id;
    private String companyId;
    private  List<CardItem> cardItems;

}
