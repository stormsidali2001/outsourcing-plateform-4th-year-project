package com.example.companymicroservice.Entities.companyField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "company_fields")
@Builder @NoArgsConstructor @AllArgsConstructor
public class CompanyField {
    @Id
    private String id;

    private String name;
}
