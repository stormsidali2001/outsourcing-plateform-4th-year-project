package com.example.companymicroservice.dto;

import com.example.companymicroservice.types.CompanyType;
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

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "field is required")
    private String field;

    @NotBlank(message = "website is required")
    private String website;

    @NotBlank(message = "type is required")
    private CompanyType type;

    @Positive(message = "size is required")
    private Integer size;

    @Valid
    private Collection<SocialMediaLinkDto> socialMediaLinks;

    @NotBlank(message = "type is required")
    private String userId;

}
