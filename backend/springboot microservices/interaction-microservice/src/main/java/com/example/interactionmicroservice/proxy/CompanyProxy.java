package com.example.interactionmicroservice.proxy;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "company-microservice")
@LoadBalancerClient(name = "company-microservice")
public interface CompanyProxy {

//    get the IDs of the existing companies
    @GetMapping("/companies")
    List<String> getCompanies();

}