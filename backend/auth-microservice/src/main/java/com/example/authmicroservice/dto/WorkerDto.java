package com.example.authmicroservice.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class WorkerDto  {
    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String phoneNumber;


    @Positive(message = "public price must be ")
    private Integer publicPrice; //by hour

    @Valid
    private Collection<WorkExperienceDto> workExperiences;

    @Valid
    private Collection<EducationDetailDto> educationDetails;

    @Valid
    private Collection<PortfolioProjectDto> portfolioProjects;

    @Valid
    private AddressDto address;

    @Valid
    private Collection<CertificationDto> certifications;

    @NotEmpty(message = "At least one skill is required")
    @Valid
    private Collection<SkillDto> skills;



}
