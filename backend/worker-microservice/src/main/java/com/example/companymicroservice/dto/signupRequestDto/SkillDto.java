package com.example.companymicroservice.dto.signupRequestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class SkillDto {

    @NotBlank(message = "skill name is required")
    private String name;

    @NotBlank(message = "skill category is required")
    private String category;

}
