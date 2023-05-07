package com.example.workermicroservice.dto;

import com.example.workermicroservice.Entities.company.SocialMediaLink;
import com.example.workermicroservice.types.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Collection;

@Builder  @AllArgsConstructor @NoArgsConstructor @Data
public class SignUpCompanyDto {
    @Id
    private String id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "field is required")
    private String field;

    @NotBlank(message = "website is required")
    private String website;

    @NotBlank(message = "type is required")
    private CompanyType type;

    @NotBlank(message = "field is required")
    private String companyField;

    @Positive(message = "size is required")
    private Integer size;

    @Valid
    private Collection<SocialMediaLinkDto> socialMediaLinks;

}
