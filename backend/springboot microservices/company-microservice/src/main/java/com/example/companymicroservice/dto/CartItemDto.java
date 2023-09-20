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
public class CartItemDto {

    @NotBlank(message = "firstName is required")
    private String workerId;
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;
    @NotBlank(message = "price is required")
    private float publicPrice;

    @NotBlank(message = "Category is required")
    private  String category;
    @NotBlank(message = "skills is required")
    private List<skillDto> skills=new ArrayList<>();



}
