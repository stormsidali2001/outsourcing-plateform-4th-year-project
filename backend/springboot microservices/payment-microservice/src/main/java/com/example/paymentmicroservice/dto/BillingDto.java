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

    private Long idBill;


    @NotBlank(message = "Company is required")
    private String idCompany;

 @NotBlank(message = "the Bill Amount is required")
    private double billAmount;

    private Date createdAt;


}
