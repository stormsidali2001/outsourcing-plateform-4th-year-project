package com.example.workermicroservice.Proxy;

import dto.RegisterUserDto;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-microservice")
@LoadBalancerClient(name="auth-microservice")
public interface AuthProxy {

    @PostMapping("/")
    public void registerUser(@RequestBody RegisterUserDto data);
}
