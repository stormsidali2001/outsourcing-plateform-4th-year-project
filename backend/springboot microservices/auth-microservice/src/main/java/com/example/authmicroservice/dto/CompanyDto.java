package com.example.authmicroservice.dto;

import com.example.authmicroservice.types.CompanyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder  @AllArgsConstructor @NoArgsConstructor @Data
public class CompanyDto {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "field is required")
    private String field;

    @NotBlank(message = "website is required")
    private String website;

    @NotNull(message = "type is required")
    private CompanyType type;


    @NotNull(message = "size is required")
    @Positive(message = "size should be positive")
    private Integer size;


    @NotNull(message = "social media links are required")
    @Valid
    private Collection<SocialMediaLinkDto> socialMediaLinks;

    private String userId;

    private boolean isNotAdmin = false;

}
