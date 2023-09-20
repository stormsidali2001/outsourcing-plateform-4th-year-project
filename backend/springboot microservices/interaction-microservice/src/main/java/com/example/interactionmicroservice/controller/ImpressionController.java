package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.service.ImpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ImpressionController {

    @Autowired
    private  ImpressionService impressionService;
    @PostMapping("new-impression")
    public ResponseEntity<String> newImpression(@RequestHeader("x-userid") String userId,@RequestHeader("x-role") String role,@RequestBody ImpressionDto impressionDto){
        if(!role.equals("COMPANY") ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("should be a company");
        }
        System.out.println("body>>>>>"+impressionDto.getIdWorker());
        impressionService.newImpression(impressionDto,userId);
      return  ResponseEntity.ok("Impression added successfully");
    }
//    @GetMapping("/impression")
//    public int nbrOfImpressions(@RequestParam("idWorker") String idWorker ){
//        System.out.println("body>>>>>"+idWorker);
//       return impressionService.getImpressionsCountByIdWorker(idWorker);
//
//    }
    @GetMapping("/nbrOfImpressions")
    public int nbrOfImpressions(@RequestParam("idWorker") String idWorker){
        System.out.println("body>>>>>"+idWorker);
        return impressionService.getImpressionsCountByIdWorker(idWorker);

    }

    @GetMapping("/WorkersNbrImpressions")
    public List<Object[]> WorkerNbrImpressions(@RequestParam String Workers){
        return impressionService.getWorkersNbrImpressions(Workers);

    }



}
