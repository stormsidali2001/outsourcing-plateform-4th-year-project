package com.example.workermicroservice.Entities.worker;

import com.example.workermicroservice.dtos.SkillDto;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Builder @Data
public class Certification {
    private String title;

    private String url;

    private Date issuedAt;

    private String companyName;

    private Collection<Skill> skills;
}
