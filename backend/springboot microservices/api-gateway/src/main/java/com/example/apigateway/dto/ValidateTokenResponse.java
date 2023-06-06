package com.example.apigateway.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ValidateTokenResponse {
    private String email;
    private String role;

    private String userId;
}
