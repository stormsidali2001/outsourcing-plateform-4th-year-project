package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ImpressionController {

    @Autowired
    private  ImpressionService impressionService;
    @PostMapping("new-Impression")
    public String newImpression(@RequestBody ImpressionDto impressionDto){
        System.out.println("body>>>>>"+impressionDto.getIdImpression().getIdWorker());
        System.out.println("body>>>>>"+impressionDto.getIdImpression().getIdCompany());
        impressionService.newImpression(impressionDto);
        return "Added";
    }
    @GetMapping("Impression/{idWorker}")
    public List<Object[]> getImpression(@PathVariable("idWorker") String idWorker ){
        System.out.println("body>>>>>"+idWorker);
       return impressionService.getImpressionByIdWorker(idWorker);

    }

}
