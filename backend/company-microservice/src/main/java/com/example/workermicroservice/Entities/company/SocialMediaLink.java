package com.example.workermicroservice.Entities.company;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
public class SocialMediaLink {
    private String name;

    private String url;
}
