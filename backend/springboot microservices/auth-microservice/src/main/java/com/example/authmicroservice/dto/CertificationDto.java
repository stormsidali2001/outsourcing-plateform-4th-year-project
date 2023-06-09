package com.example.authmicroservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Builder @Data @AllArgsConstructor @NoArgsConstructor
public class CertificationDto {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "url is required")
    private String url;

    @PastOrPresent(message = "issuedAt can't be in the future")
    private Date issuedAt;

    @NotBlank(message = "company name is required")
    private String companyName;

    @NotNull(message = "skills  are required")
    @Valid
    private Collection<SkillDto> skills;
}
