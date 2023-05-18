package com.example.authmicroservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;
import java.util.Date;


@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class WorkExperienceDto {

    @NotBlank(message = "title is required ")
    private String title;

    @PastOrPresent(message = "start date can't be in the future")
    private Date startDate;

    @PastOrPresent(message = "start date can't be in the futrue")
    private Date endDate;

    @NotBlank(message = "company name is required")
    private String companyName;

    @NotBlank(message = "description is required")
    @Length(min = 200,message = "description should be at least 200 characters long")
    private String description;

    @NotBlank(message = "location is required")
    private String location;

    @NotBlank(message = "type is required")
    private String type;

    @Valid
    @NotEmpty(message = "one skill at least is required")
    private Collection<SkillDto> skills;

}
