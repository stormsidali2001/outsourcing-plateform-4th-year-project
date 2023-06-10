package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishController {


      @Autowired
    WishService wishService;
    @PostMapping("new-wish")
    public ResponseEntity<String> newWish(@RequestHeader("x-userid") String userId,@RequestHeader("x-role") String role,@RequestBody WishDto wishDto){
        if(!role.equals("COMPANY") ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("should be a company");
        }
        return wishService.newWish(wishDto,userId);
    }


    @GetMapping("/nbrOfWishes")
    public int nbrOfWishes(@RequestParam("idWorker") String idWorker){
        return wishService.getWishesCountByIdWorker(idWorker);

    }

    @GetMapping("/WorkersNbrWishes")
    public List<Object[]> WorkerNbrWishes(@RequestParam("idWorker") String Workers){
        return wishService.getWorkersNbrWishes(Workers);

    }

}
