package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder @Data
public class PortfolioProjectDto {
    private String title;

    private String role;

    private String projectGoal;

    private String projectSolution;

    private Collection<SkillDto> skills;
}
