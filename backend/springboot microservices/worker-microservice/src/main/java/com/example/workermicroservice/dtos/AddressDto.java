package com.example.workermicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder @Data @NoArgsConstructor @AllArgsConstructor
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