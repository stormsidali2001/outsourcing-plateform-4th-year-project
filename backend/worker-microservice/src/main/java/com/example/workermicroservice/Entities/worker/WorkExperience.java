package com.example.workermicroservice.Entities.worker;

import lombok.Builder;

import java.util.Date;

@Builder
public class WorkExperience {
    private String title;

    private Date startDate;

    private Date endDate;

    private String companyName;

    private String description;

    private String location;

    private String type;
}
