package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;

import java.util.Date;

public class CertificationDto {
    private String title;

    private String url;

    private Date issuedAt;

    private String companyName;

    private SkillDto skill;
}
