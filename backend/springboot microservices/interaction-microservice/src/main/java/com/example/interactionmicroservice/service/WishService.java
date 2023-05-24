package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.repositories.WishRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class WishService {
  @Autowired
    WishRepo wishRepo;
    public void newWish(WishDto wishDto){
        String Id = UUID.randomUUID().toString();
        Wish wish= Wish.builder()
                .idWish(Id)
                .idWorker(wishDto.getIdWorker())
                .idCompany(wishDto.getIdCompany())
                .createdAt(new Date())
                .build();

        wishRepo.save(wish);
    }



    public int getWishesCountByIdWorker(String idWorker){

        int NbrWishes= wishRepo.countWishByIdWorker(idWorker) ;
        System.out.println("size"+NbrWishes);
        return NbrWishes;
    }
    public List<Object[]> getWorkersNbrWishes(String workerIds){

        // Split the comma-separated IDs into an array
        String[] ids = workerIds.split(",");

        return wishRepo.getWishCountByWorkers(List.of(ids));

    }



}
