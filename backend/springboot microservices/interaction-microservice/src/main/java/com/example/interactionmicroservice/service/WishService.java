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

    public List<Wish> getWishesByIdWorker(String idWorker){

        List<Wish> wishes= wishRepo.findWishesByIdWorker(idWorker) ;
        System.out.println("size"+wishes.size());
        return wishes;
    }



}
