package com.example.workermicroservice.Entities.worker;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder @Data
public class PortfolioProject {
    private String title;

    private String role;

    private String projectGoal;

    private String imageUrl;

    private String projectSolution;

    private Collection<Skill> skills;


}
