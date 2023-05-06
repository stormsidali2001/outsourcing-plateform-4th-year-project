package com.example.authmicroservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
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
