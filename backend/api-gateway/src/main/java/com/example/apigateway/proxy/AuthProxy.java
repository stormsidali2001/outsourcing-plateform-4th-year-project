package com.example.apigateway.proxy;

import com.example.apigateway.config.LBConfiguration;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-microservice")
@LoadBalancerClient(name = "auth-microservice",configuration = LBConfiguration.class)
public interface AuthProxy {
    @GetMapping("/validate-token")
    public Object  validateToken(@RequestParam("token") String token);
}
