package com.example.companymicroservice.controller;


import com.example.companymicroservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class companyController {
    @Autowired
    private CompanyService companyService;
    @GetMapping("company-exists/{userId}")
    boolean getCompanyExists(@PathVariable("userId") String userId){
        return  this.companyService.getCompanyExists(userId);
    }

}
