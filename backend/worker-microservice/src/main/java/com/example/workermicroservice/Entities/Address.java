package com.example.workermicroservice.Entities;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Address {
    private String wilaya;

    private String commune;

    private String code_postal;

    private String addressDomissile;

    private String numRue;
}
