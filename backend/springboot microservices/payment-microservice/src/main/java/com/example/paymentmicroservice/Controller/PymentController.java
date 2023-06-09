package com.example.paymentmicroservice.Controller;


import com.example.paymentmicroservice.Entities.Billing;
import com.example.paymentmicroservice.Services.PaymentService;
import com.example.paymentmicroservice.dto.BillingDto;
import com.example.paymentmicroservice.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
public class PymentController {

    @Autowired
    PaymentService paymentService;




    @PostMapping("new-transaction/{idCompany}")
    public String newTransaction(@RequestHeader("idCompany") String idCompany, @RequestBody TransactionDto transactionDto){
        return paymentService.newTransaction(transactionDto,idCompany);
    }

    @PostMapping("new-billing")
    public String newBilling(@RequestBody BillingDto billingDto )
    {
        return paymentService.newBilling(billingDto);
    }
    @GetMapping("all")
    public List<Billing> getBillings(){
        return this.paymentService.getBillings();
    }

    @GetMapping("status/{idCompany}")
    public double getStatus(@PathVariable("idCompany") String idCompany)
    {
        return paymentService.getPaymentStatus(idCompany);
    }


}
