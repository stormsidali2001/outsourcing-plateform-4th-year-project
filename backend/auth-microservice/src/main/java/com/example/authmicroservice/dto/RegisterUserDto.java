package com.example.authmicroservice.dto;

import com.example.authmicroservice.types.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String password;
    private String email;
    private Role role;
}
