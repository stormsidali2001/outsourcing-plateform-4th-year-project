package com.example.interactionmicroservice.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "worker-microservice",url = "localhost:5001")
public interface WorkerProxy {

    @GetMapping("/worker-exist")
       Boolean workerExist(@RequestParam("idWorker") String idWorker);

}
