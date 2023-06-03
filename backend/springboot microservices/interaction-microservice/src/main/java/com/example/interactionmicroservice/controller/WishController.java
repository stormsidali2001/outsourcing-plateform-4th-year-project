package com.example.interactionmicroservice.controller;


import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WishController {


      @Autowired
    WishService wishService;
    @PostMapping("new-wish")
    public String newWish(@RequestBody WishDto wishDto){

        return wishService.newWish(wishDto);
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
