package com.example.workermicroservice.dto.signupRequestDto;


import com.example.workermicroservice.Entities.worker.*;
import com.example.workermicroservice.types.WorkerStatus;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Builder @Data
public class SignUpRequestDto {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private WorkerStatus status;

    private Integer intialPrice;//by hour

    private Integer publicPrice;

    private Date signUpDate;

    private Collection<WorkExperienceDto> workExperiences;

    private Collection<EducationDetailDto> educationDetails;

    private Collection<PortfolioProjectDto> portfolioProjects;

    private AddressDto address;

    private Collection<CertificationDto> collections;

    private Collection<SkillDto> skills;



}
