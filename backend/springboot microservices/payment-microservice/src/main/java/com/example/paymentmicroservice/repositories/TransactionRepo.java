package com.example.paymentmicroservice.repositories;

import com.example.paymentmicroservice.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction,String> {


    @Query("select sum (t.amount) from Transaction  t ,Billing b where b.idCompany= :idCompany")
    double getPaymentStatus(String idCompany);

}
