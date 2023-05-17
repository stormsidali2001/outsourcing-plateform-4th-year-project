package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Click;
import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.ClickDto;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.repositories.ClickRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ClickService {

    @Autowired
    ClickRepo clickRepo;
    public void newClick(ClickDto clickDto){
        String Id = UUID.randomUUID().toString();
        Click click= Click.builder()
                .idClick(Id)
                .idWorker(clickDto.getIdWorker())
                .idCompany(clickDto.getIdCompany())
                .createdAt(new Date())
                .build();

        clickRepo.save(click);
    }

    public List<Click> getClicksByIdWorker(String idWorker){

        List<Click> clicks=  clickRepo.findClicksByIdWorker(idWorker);
        System.out.println("size"+clicks.size());
        return clicks;
    }



}
