package com.example.paymentmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDto {


    @NotBlank(message = "Company is required")
    private String idCompany;

    @NotBlank(message = "jobRequestId is required")
    private String jobRequestId;

    @NotBlank(message = "the Bill Amount is required")
    private double billAmount;

}
