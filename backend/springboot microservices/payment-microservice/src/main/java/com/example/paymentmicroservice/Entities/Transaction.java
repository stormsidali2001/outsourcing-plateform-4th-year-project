package com.example.paymentmicroservice.Entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long idTransaction;

  private double amount;

  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "billing_id")
  private Billing billing;

}
