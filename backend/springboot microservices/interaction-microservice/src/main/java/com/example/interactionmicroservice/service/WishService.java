package com.example.interactionmicroservice.service;

import com.example.interactionmicroservice.Entities.Wish;
import com.example.interactionmicroservice.dto.WishDto;
import com.example.interactionmicroservice.events.InteractionAddedEvent;
import com.example.interactionmicroservice.proxy.CompanyProxy;
import com.example.interactionmicroservice.proxy.WorkerProxy;
import com.example.interactionmicroservice.repositories.WishRepo;
import com.example.interactionmicroservice.types.InteractionId;
import com.example.interactionmicroservice.types.InteractionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class WishService {
  @Autowired
    WishRepo wishRepo;
  @Autowired
  private WorkerProxy workerProxy;

  @Autowired
  private CompanyProxy companyProxy;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;
    public ResponseEntity<String> newWish(WishDto wishDto, String companyId){

        if(workerProxy.workerExist(wishDto.getIdWorker())){
            InteractionId idWish=new InteractionId(companyId,wishDto.getIdWorker());
            if(!wishRepo.existsById(idWish)) {
                Wish wish = Wish.builder()
                        .idWish(idWish)
                        .createdAt(new Date())
                        .build();

                Wish w =  wishRepo.save(wish);
                kafkaTemplate.send("interaction_added", InteractionAddedEvent.builder()
                        .companyId(w.getIdWish().getIdCompany())
                        .workerId(w.getIdWish().getIdWorker())
                        .interactionType(InteractionType.WISH)
                        .build());
            }else {
                wishRepo.deleteById(idWish);
                return ResponseEntity.ok("deja existe");
            }

    }
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    public int getWishesCountByIdWorker(String idWorker){

        int NbrWishes= wishRepo.countWishByIdWish_IdWorker(idWorker) ;
        System.out.println("size"+NbrWishes);
        return NbrWishes;
    }
    public List<Object[]> getWorkersNbrWishes(String workerIds){

        // Split the comma-separated IDs into an array
        String[] ids = workerIds.split(",");

        return wishRepo.getWishCountByWorkers(List.of(ids));

    }




}
