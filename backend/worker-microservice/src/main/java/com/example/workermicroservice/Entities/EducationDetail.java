package com.example.workermicroservice.Entities;

import lombok.Builder;

import java.util.Date;

@Builder
public class EducationDetail {
    private String description;

    private Date startDate;

    private Date endDate;

    private String school;

    private String location;

    private String field;
}
