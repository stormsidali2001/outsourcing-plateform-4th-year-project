package com.example.interactionmicroservice.proxy;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@LoadBalancerClient(name="worker-microservice")
@FeignClient(name = "worker-microservice")
public interface WorkerProxy {

    @GetMapping("/worker-exist")
       Boolean workerExist(@RequestParam("idWorker") String idWorker);

}
