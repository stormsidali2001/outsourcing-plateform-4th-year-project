package com.example.workermicroservice.Entities.company;

import com.example.workermicroservice.types.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document @Builder
@NoArgsConstructor @AllArgsConstructor
public class Company {
    @Id
    private String id;

    private String name;

    private String field;

    private String website;

    private CompanyType type;

    private String companyField;

    private Integer size;

    private Collection<SocialMediaLink> socialMediaLinks;

}
