package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.repositories.ImpressionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ImpressionService {


    @Autowired
    private ImpressionRepo impressionRepo;

    public ResponseEntity<String> newImpression(ImpressionDto impressionDto){
        Impression impression= Impression.builder()
                .idImpression(impressionDto.getIdImpression())
                .createdAt(new Date())
                .build();

        impressionRepo.save(impression);



        return ResponseEntity.ok("Impression added successfully");
    }

    public List<Object[]> getImpressionByIdWorker(String idWorker){

        List<Object[]> impressions=impressionRepo.findImpressionsByIdWorker(idWorker);
        System.out.println("size"+impressions.size());
        return impressions;
    }



}
