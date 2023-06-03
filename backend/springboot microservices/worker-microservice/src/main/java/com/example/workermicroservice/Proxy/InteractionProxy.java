package com.example.workermicroservice.Proxy;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "interaction-microservice", url = "localhost:5003")
public interface InteractionProxy {

    @GetMapping("/nbrOfImpressions")
   int getNbrImpressions(@RequestParam("idWorker") String idWorker);

    @GetMapping("/nbrOfWishes")
    int getNbrWishes(@RequestParam("idWorker") String idWorker);

    @GetMapping("/nbrOfClicks")
    int getNbrClicks(@RequestParam("idWorker") String idWorker);



}
