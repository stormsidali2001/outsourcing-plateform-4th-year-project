package com.example.workermicroservice.Entities;

import lombok.Builder;
import lombok.Data;

import java.beans.Transient;

@Builder @Data
public class PortfolioProject {
    private String title;

    private String role;

    private String projectGoal;

    private String projectSolution;


}
