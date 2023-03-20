package com.example.workermicroservice.dto.signupRequestDto;

import com.example.workermicroservice.Entities.worker.Skill;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Builder @Data
public class PortfolioProjectDto {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "role is required")
    private String role;

    @NotBlank(message= "project gloal is required")
    @Length(min = 2000,message = "the project goal description should have at least 2000 characters")
    private String projectGoal;

    @NotBlank(message= "project gloal is required")
    @Length(min = 2000,message = "the project solution description should have at least 2000 characters")
    private String projectSolution;

    @NotEmpty(message = "you should at least add one skill")
    @Valid
    private Collection<SkillDto> skills;
}
