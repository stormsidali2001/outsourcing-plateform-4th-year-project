package com.example.companymicroservice.Proxy.Interaction;


import com.example.companymicroservice.models.ClickModel;
import com.example.companymicroservice.models.ImpressionModel;
import com.example.companymicroservice.models.WishModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "interaction-microservice", url = "localhost:5003/api")
public interface InteractionProxy {

    @GetMapping("impression")
    List<ImpressionModel> getImpressions(@RequestParam("idWorker") String idWorker,
                                         @RequestParam("projection") String projection);

    @GetMapping("wish")
    List<WishModel> getWishes(@RequestParam("idWorker") String idWorker,
                              @RequestParam("projection") String projection);

    @GetMapping("click")
    List<ClickModel> getClicks(@RequestParam("idWorker") String idWorker,
                               @RequestParam("projection") String projection);



}
