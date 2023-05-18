package com.example.workermicroservice.Entities.worker;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Builder@Data
public class EducationDetail {
    private String description;

    private Date startDate;

    private Date endDate;

    private String school;

    private String location;

    private String field;

    private Collection<Skill> skills;
}
