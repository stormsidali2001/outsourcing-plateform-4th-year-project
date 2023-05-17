package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.Entities.Click;
import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.ClickDto;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.service.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ClickController {

    @Autowired
    ClickService clickService;

    @PostMapping("new-click")
    public ResponseEntity<String> newWish(@RequestBody ClickDto clickDto){
        clickService.newClick(clickDto);
        return  ResponseEntity.ok("Click added successfully");
    }
    @GetMapping("/click")
    public List<Click> getClick(@RequestParam("idWorker") String idWorker ){
        return clickService.getClicksByIdWorker(idWorker);

    }
}
