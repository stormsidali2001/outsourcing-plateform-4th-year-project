package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Builder @Data
public class EducationDetailDto {
    private String description;

    private Date startDate;

    private Date endDate;

    private String school;

    private String location;

    private String field;

    private Collection<SkillDto> skills;
}
