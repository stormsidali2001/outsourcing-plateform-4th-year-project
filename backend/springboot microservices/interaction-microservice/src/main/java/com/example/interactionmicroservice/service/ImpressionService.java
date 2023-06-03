package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.proxy.CompanyProxy;
import com.example.interactionmicroservice.proxy.WorkerProxy;
import com.example.interactionmicroservice.repositories.ImpressionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImpressionService {


    @Autowired
    private ImpressionRepo impressionRepo;
  @Autowired
   private WorkerProxy workerProxy;

  @Autowired
  private CompanyProxy companyProxy;
    public void newImpression(ImpressionDto impressionDto){

        if(workerProxy.workerExist(impressionDto.getIdWorker())){
            String Id = UUID.randomUUID().toString();
            Impression impression= Impression.builder()
                    .idImpression(Id)
                    .idWorker(impressionDto.getIdWorker())
                    .idCompany(impressionDto.getIdCompany())
                    .createdAt(new Date())
                    .build();

            impressionRepo.save(impression);
        }else{
            System.out.println("user don't exist"+workerProxy.workerExist(impressionDto.getIdWorker()));
        }

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
