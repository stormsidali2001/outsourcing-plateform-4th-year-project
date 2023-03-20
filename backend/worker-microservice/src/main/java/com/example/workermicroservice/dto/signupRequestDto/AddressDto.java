package com.example.workermicroservice.dto.signupRequestDto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder @Data
public class AddressDto {
    @NotBlank(message = "Wilaya is required")
    private String wilaya;

    @NotBlank(message = "Commune is required")
    private String commune;

    @NotBlank(message = "Postal code is required")
    private String code_postal;

    @NotBlank(message = "Address is required")
    private String addressDomissile;

    @NotBlank(message = "Street number is required")
    private String numRue;
}