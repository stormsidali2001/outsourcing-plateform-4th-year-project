package com.example.companymicroservice.Entities.worker;

import lombok.Builder;

import java.util.Collection;
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

    private Collection<Skill>  skills;
}
