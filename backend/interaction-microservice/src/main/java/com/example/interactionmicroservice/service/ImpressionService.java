package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.repositories.ImpressionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImpressionService {


    @Autowired
    private ImpressionRepo impressionRepo;

    public void newImpression(ImpressionDto impressionDto){
        String Id = UUID.randomUUID().toString();
        Impression impression= Impression.builder()
                .idImpression(Id)
                .idWorker(impressionDto.getIdWorker())
                .idCompany(impressionDto.getIdCompany())
                .createdAt(new Date())
                .build();

        impressionRepo.save(impression);
    }

    public List<Impression> getImpressionsByIdWorker(String idWorker){

        List<Impression> impressions= impressionRepo.findImpressionsByIdWorker(idWorker) ;
        System.out.println("size"+impressions.size());
        return impressions;
    }



}
