package com.example.workermicroservice.Entities.worker;

import com.example.workermicroservice.types.WorkerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.Date;

@Document(value="worker")
@Builder @Data  @AllArgsConstructor@NoArgsConstructor
public class Worker {
    @Id
    private String id;

    private String firstName;

    private String category;

    private String title;

    private String cvUrl;

    private String pictureUrl;

    private String cardIdUrl;

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

    private String userId;
    @Transient
    private int nbrImpressions;
    @Transient
    private int nbrWishes;
    @Transient
    private int nbrClicks;
}
