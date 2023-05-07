package com.example.authmicroservice.dto;

import com.example.authmicroservice.types.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "email is required")
    private String email;

}
