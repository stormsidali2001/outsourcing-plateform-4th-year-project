package com.example.workermicroservice.Entities;

import lombok.Builder;

import java.util.Date;

@Builder
public class Certification {
    private String title;

    private String url;

    private Date issuedAt;

    private String companyName;
}
