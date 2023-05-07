package com.example.workermicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class SocialMediaLinkDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "url is required")
    private String url;
}
