package com.example.workermicroservice.Entities;

import com.example.workermicroservice.types.WorkerStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

@Document(value="worker")
@Builder @Data
public class Woker {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private WorkerStatus status;

    private Integer intialPrice;//by hour

    private Integer publicPrice;

    private Date signUpDate;

    private Collection<WorkExperience> workExperiences;

    private Collection<EducationDetail> educationDetails;

    private Collection<PortfolioProject> portfolioProjects;

    private Address address;

    private Collection<Certification> collections;





}
