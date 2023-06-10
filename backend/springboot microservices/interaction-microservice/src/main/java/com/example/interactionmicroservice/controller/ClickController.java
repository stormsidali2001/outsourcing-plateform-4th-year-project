package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.Entities.Click;
import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.ClickDto;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class ClickController {

    @Autowired
    ClickService clickService;

    @PostMapping("new-click")
    public ResponseEntity<String> newWish(@RequestHeader("x-userid") String userId,@RequestHeader("x-role") String role,@RequestBody ClickDto clickDto){
        if(!role.equals("COMPANY") ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("should be a company");
        }
        clickService.newClick(clickDto,userId);
        return  ResponseEntity.ok("Click added successfully");
    }
//    @GetMapping("/click")
//    public List<Click> getClick(@RequestParam("idWorker") String idWorker ){
//        return clickService.getClicksByIdWorker(idWorker);
//
//    }



    @GetMapping("/nbrOfClicks")
    public int nbrOfClicks(@RequestParam("idWorker") String idWorker){
        System.out.println("body>>>>>"+idWorker);
        return clickService.getClicksCountByIdWorker(idWorker);

    }

    @GetMapping("/WorkersNbrClicks")
    public List<Object[]> WorkerNbrClicks(@RequestParam("idWorker") String Workers){
        return clickService.getWorkersNbrClicks(Workers);

    }
}
