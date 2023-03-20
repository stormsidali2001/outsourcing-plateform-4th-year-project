package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Builder @Data
public class CertificationDto {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "url is required")
    private String url;

    @PastOrPresent(message = "issuedAt can't be in the future")
    private Date issuedAt;

    @NotBlank(message = "company name is required")
    private String companyName;

    @NotNull(message = "skill  is required")
    @Valid
    private SkillDto skill;
}
