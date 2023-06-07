package com.example.authmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
public class Email {
    private String userId;
    private String email;
}
