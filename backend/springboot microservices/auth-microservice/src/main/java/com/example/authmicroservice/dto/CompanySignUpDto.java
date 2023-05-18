package com.example.authmicroservice.dto;

import com.example.authmicroservice.types.CompanyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @NoArgsConstructor @AllArgsConstructor @Data
public class CompanySignUpDto {
    @NotNull(message = "user required")
    @Valid
    private RegisterUserDto user;

    @NotNull(message = "company required")
    @Valid
    private CompanyDto company;
}
