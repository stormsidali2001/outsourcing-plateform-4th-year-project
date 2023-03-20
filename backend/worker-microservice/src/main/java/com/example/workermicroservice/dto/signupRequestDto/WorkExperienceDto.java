package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Date;


@Builder @Data
public class WorkExperienceDto {
    private String title;

    private Date startDate;

    private Date endDate;

    private String companyName;

    private String description;

    private String location;

    private String type;
    private Collection<SkillDto> skills;

}
