package com.example.workermicroservice.dto.signupRequestDto;


import com.example.workermicroservice.Entities.worker.*;
import com.example.workermicroservice.types.WorkerStatus;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Date;

@Builder @Data
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

    private Collection<CertificationDto> collections;

    @NotEmpty(message = "At least one skill is required")
    @Valid
    private Collection<SkillDto> skills;



}
