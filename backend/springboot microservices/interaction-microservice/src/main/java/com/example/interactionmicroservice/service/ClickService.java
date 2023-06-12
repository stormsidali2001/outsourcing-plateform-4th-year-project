package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Click;
import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.ClickDto;
import com.example.interactionmicroservice.dto.ImpressionDto;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.events.InteractionAddedEvent;
import com.example.interactionmicroservice.proxy.CompanyProxy;
import com.example.interactionmicroservice.proxy.WorkerProxy;
import com.example.interactionmicroservice.repositories.ClickRepo;
import com.example.interactionmicroservice.types.InteractionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ClickService {

    @Autowired
    ClickRepo clickRepo;
    @Autowired
    private WorkerProxy workerProxy;

    @Autowired
    private CompanyProxy companyProxy;

   @Autowired
   private KafkaTemplate<Object, Object> kafkaTemplate;
    public void newClick(ClickDto clickDto,String userId){

        if(workerProxy.workerExist(clickDto.getIdWorker())) {
            String Id = UUID.randomUUID().toString();
            Click click = Click.builder()
                    .idClick(Id)
                    .idWorker(clickDto.getIdWorker())
                    .idCompany(userId)
                    .createdAt(new Date())
                    .build();

            clickRepo.save(click);
            kafkaTemplate.send("interaction-added", InteractionAddedEvent.builder()
                            .companyId(click.getIdCompany())
                            .workerId(click.getIdWorker())
                            .interactionType(InteractionType.CLICK)
                    .build());
        }
    }

    public int getClicksCountByIdWorker(String idWorker){

        int NbrClicks= clickRepo.countClickByIdWorker(idWorker) ;
        System.out.println("size"+NbrClicks);
        return NbrClicks;
    }
    public List<Object[]> getWorkersNbrClicks(String workerIds){

        // Split the comma-separated IDs into an array
        String[] ids = workerIds.split(",");

        return clickRepo.getClickCountByWorkers(List.of(ids));

    }





}
