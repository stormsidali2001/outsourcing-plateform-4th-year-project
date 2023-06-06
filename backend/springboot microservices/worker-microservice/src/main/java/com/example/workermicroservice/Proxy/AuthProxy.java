package com.example.workermicroservice.Proxy;


import com.example.workermicroservice.Projections.Email;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "auth-microservice",url="localhost:8082")
//@LoadBalancerClient(name = "auth-microservice")
public interface AuthProxy {

        @GetMapping("workers/emails")
        List<Email> getWorkerEmail(@RequestParam("ids") String ids);

}
