package com.example.workermicroservice.dto.signupRequestDto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder @Data
public class SkillDto {

    @NotBlank(message = "skill name is required")
    private String name;

    @NotBlank(message = "skill category is required")
    private String category;

}
