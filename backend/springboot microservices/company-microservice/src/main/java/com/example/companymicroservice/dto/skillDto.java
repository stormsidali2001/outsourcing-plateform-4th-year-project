package com.example.companymicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class skillDto {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Category is required")
    private String category;
}
