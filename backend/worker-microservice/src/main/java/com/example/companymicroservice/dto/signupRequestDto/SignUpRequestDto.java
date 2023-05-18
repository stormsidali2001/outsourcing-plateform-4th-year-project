package com.example.workermicroservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Collection;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class SignUpRequestDto {
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

    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "category is required")
    private String category;


}
