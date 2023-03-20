package com.example.workermicroservice.dto.signupRequestDto;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class AddressDto {
    private String wilaya;

    private String commune;

    private String code_postal;

    private String addressDomissile;

    private String numRue;
}
