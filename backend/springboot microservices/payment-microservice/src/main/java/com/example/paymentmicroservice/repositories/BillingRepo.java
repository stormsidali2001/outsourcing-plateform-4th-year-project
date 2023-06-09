package com.example.paymentmicroservice.repositories;

import com.example.paymentmicroservice.Entities.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillingRepo extends JpaRepository<Billing,String> {
   @Query("select b.billAmount from Billing b where b.idCompany= :idCompany")
   double getBillAmount(String idCompany);
   Billing findBillingByIdCompany(String idCompany);
   boolean existsBillingByIdCompany(String idCompany);

   List<Billing> findAllByJobRequestIdIn(List<String> jobRequestIds);


}
