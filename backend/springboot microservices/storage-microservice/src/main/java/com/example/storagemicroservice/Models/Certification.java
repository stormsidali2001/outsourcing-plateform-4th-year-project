package com.example.storagemicroservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class Certification {
    private String title;

    private String url;

    private Date issuedAt;

    private String companyName;
}
