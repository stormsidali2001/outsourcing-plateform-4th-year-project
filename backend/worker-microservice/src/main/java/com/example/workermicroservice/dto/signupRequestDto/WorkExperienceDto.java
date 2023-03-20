package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;

import java.util.Collection;
import java.util.Date;

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
