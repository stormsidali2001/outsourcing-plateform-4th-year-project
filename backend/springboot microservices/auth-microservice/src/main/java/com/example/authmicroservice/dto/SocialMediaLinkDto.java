package com.example.authmicroservice.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class SocialMediaLinkDto {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "url is required")
    private String url;
}
