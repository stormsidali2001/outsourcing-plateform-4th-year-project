package com.example.authmicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class SkillDto {

    @NotBlank(message = "skill name is required")
    private String name;

    @NotBlank(message = "skill category is required")
    private String category;

}
