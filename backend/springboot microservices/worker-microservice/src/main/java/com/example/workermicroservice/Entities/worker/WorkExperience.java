package com.example.workermicroservice.Entities.worker;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Builder @Data
public class WorkExperience {
    private String title;

    private Date startDate;

    private Date endDate;

    private String companyName;

    private String description;

    private String location;

    private String type;

}
