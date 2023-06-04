package com.example.companymicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDto {

    @NotBlank(message = "name is required")
    private String companyId;
    @NotBlank(message = "name is required")
    private List<CartItemDto> cartItems;


}
