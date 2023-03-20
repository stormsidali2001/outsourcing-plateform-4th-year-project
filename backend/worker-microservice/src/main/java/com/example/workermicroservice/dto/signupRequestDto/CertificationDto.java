package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder @Data
public class CertificationDto {
    private String title;

    private String url;

    private Date issuedAt;

    private String companyName;

    private SkillDto skill;
}
