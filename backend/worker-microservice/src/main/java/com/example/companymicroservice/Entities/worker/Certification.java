package com.example.companymicroservice.Entities.worker;

import lombok.Builder;

import java.util.Date;

@Builder
public class Certification {
    private String title;

    private String url;

    private Date issuedAt;

    private String companyName;

    private Skill skill;
}
