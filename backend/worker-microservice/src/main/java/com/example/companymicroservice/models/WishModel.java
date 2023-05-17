package com.example.companymicroservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishModel {
    private String idWorker;

    private String idCompany;

    private Date createdAt;
}
