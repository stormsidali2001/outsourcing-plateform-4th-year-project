package com.example.paymentmicroservice.Services;


import com.example.paymentmicroservice.Entities.Billing;
import com.example.paymentmicroservice.Entities.Transaction;
import com.example.paymentmicroservice.dto.BillingDto;
import com.example.paymentmicroservice.dto.TransactionDto;
import com.example.paymentmicroservice.repositories.BillingRepo;
import com.example.paymentmicroservice.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    TransactionRepo transactionRepo;

    @Autowired
    BillingRepo billingRepo;

    public String newTransaction(TransactionDto transactionDto,String companyId){
        Transaction transaction = Transaction.builder()
                .idTransaction(transactionDto.getIdTransaction())
                .amount(transactionDto.getAmount())
                .createdAt(new Date())
                .build();



        // Check if the billing exists
        boolean billingExists = billingRepo.existsBillingByIdCompany(companyId);
        if (billingExists) {
            Billing billing = billingRepo.findBillingByIdCompany(companyId);
            transaction.setBilling(billing);
        } else {
            return "NO Billing found";
        }

        transactionRepo.save(transaction);

        return "Transaction added";
    }


    //create new billing
    public String newBilling(BillingDto billingDto){

        Billing billing=Billing.builder()
                .idCompany(billingDto.getIdCompany())
                .billAmount(billingDto.getBillAmount())
                .transactions(null)
                .createdAt(new Date())
                .jobRequestId(billingDto.getJobRequestId())
                .build();

     billingRepo.save(billing);
        return "Bill added";
    }


    public double getPaymentStatus(String idCompany){

         Double companyBillAmount=billingRepo.getBillAmount(idCompany);

        return companyBillAmount-transactionRepo.getPaymentStatus(idCompany);
    }
    public List<Billing> getBillings() {
        return billingRepo.findAll();
    }
    public List<Billing> getBillingsByJobRequests(List<String> jobRequestIds) {
        return billingRepo.findAllByJobRequestIdIn(jobRequestIds);
    }
}


