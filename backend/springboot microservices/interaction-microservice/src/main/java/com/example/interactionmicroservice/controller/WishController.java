package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class WishController {


      @Autowired
    WishService wishService;
    @PostMapping("new-wish")
    public ResponseEntity<String> newWish(@RequestBody WishDto wishDto){
        wishService.newWish(wishDto);
        return  ResponseEntity.ok("Wish added successfully");
    }
    @GetMapping("/wish")
    public List<Wish> getWish(@RequestParam("idWorker") String idWorker ){
        return wishService.getWishesByIdWorker(idWorker);

    }
}
