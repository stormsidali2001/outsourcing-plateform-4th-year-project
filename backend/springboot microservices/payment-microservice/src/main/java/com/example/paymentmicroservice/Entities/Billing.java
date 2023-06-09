package com.example.paymentmicroservice.Entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBill;


    private String idCompany;

    private String jobRequestId;

    private double billAmount;

    private Date createdAt;

    @OneToMany(mappedBy = "billing")
    private List<Transaction> transactions;


}
