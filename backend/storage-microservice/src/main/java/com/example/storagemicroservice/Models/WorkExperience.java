package com.example.storagemicroservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class WorkExperience {
    private String title;

    private Date startDate;

    private Date endDate;

    private String companyName;

    private String description;

    private String location;

    private String type;
}
