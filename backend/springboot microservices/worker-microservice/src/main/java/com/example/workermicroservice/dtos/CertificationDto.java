package com.example.workermicroservice.dtos;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
