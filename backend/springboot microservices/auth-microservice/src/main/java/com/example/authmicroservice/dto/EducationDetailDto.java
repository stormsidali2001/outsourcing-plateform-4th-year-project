package com.example.authmicroservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class EducationDetailDto {

    @NotBlank(message = "description is required")
    private String description;

    @PastOrPresent(message = "start date can't be in the future")
    private Date startDate;

    @PastOrPresent(message = "end date can't be in the future")
    private Date endDate;

    @NotBlank(message = "shcool is required")
    private String school;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "field is required")
    private String field;

    @Valid
    private Collection<SkillDto> skills;
}
