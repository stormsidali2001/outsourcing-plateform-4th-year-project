package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.types.InteractionId;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressionRepo extends JpaRepository<Impression, InteractionId> {
    @Query("select im from Impression im where im.idImpression.idWorker= :idWorker")
    List<Object[]>  findImpressionsByIdWorker(@Param("idWorker") String idWorker);
}
