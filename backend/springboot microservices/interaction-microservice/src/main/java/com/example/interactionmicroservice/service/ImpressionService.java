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

    public int getImpressionsCountByIdWorker(String idWorker){

        int NbrImpressions= impressionRepo.countImpressionByIdWorker(idWorker) ;
        System.out.println("size"+NbrImpressions);
        return NbrImpressions;
    }
  public List<Object[]> getWorkersNbrImpressions(String workerIds){

      // Split the comma-separated IDs into an array
      String[] ids = workerIds.split(",");

      return impressionRepo.getImpressionCountByWorkers(List.of(ids));

  }


}
