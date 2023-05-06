package com.example.authmicroservice.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.jdbc.Work;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerSignUpRequestDto {
    @Valid
    private RegisterUserDto user;

    @Valid
    private WorkerDto worker;
}
