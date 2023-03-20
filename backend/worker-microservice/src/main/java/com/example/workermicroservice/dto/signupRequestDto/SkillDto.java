package com.example.workermicroservice.dto.signupRequestDto;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class SkillDto {
    private String name;

    private String category;

}
