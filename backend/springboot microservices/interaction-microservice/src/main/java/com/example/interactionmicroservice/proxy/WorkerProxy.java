package com.example.interactionmicroservice.proxy;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "worker-microservice")
@LoadBalancerClient(name = "worker-microservice")

public interface WorkerProxy {

    @GetMapping("/worker-exist")
       Boolean workerExist(@RequestParam("idWorker") String idWorker);



    // bring the Ids of existing workers
    @GetMapping("/workers")
    List<String> getWorkers();

}
