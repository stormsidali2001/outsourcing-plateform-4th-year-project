package com.example.authmicroservice.dto;

import com.example.authmicroservice.types.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class ValidateTokenResponse {
    private   String email;
    private Role role;
}
