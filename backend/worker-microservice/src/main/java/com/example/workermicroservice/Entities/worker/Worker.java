package com.example.workermicroservice.Entities.worker;

import com.example.workermicroservice.types.WorkerStatus;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Date;

@Document(value="worker")
@Builder @Data
public class Worker {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;


    private Integer publicPrice; //by hour


    private WorkerStatus status;
    private Date signUpDate;

    private Collection<WorkExperience> workExperiences;

    private Collection<EducationDetail> educationDetails;

    private Collection<PortfolioProject> portfolioProjects;

    private Address address;

    private Collection<Certification> certifications;

    private Collection<Skill> skills;





}
