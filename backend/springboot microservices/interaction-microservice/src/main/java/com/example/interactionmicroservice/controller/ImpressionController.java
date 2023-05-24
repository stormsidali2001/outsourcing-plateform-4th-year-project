package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ImpressionController {

    @Autowired
    private  ImpressionService impressionService;
    @PostMapping("new-impression")
    public ResponseEntity<String> newImpression(@RequestBody ImpressionDto impressionDto){
        System.out.println("body>>>>>"+impressionDto.getIdCompany());
        System.out.println("body>>>>>"+impressionDto.getIdWorker());
        impressionService.newImpression(impressionDto);
      return  ResponseEntity.ok("Impression added successfully");
    }
//    @GetMapping("/impression")
//    public int nbrOfImpressions(@RequestParam("idWorker") String idWorker ){
//        System.out.println("body>>>>>"+idWorker);
//       return impressionService.getImpressionsCountByIdWorker(idWorker);
//
//    }
    @GetMapping("/nbrOfImpressions/{idWorker}")
    public int nbrOfImpressions(@PathVariable String idWorker){
        System.out.println("body>>>>>"+idWorker);
        return impressionService.getImpressionsCountByIdWorker(idWorker);

    }

    @GetMapping("/WorkersNbrImpressions")
    public List<Object[]> WorkerNbrImpressions(@RequestParam String Workers){
        return impressionService.getWorkersNbrImpressions(Workers);

    }



}
