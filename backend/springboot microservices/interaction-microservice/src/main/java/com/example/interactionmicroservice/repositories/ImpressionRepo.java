package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpressionRepo extends JpaRepository<Impression, String> {
    List<Impression> findImpressionsByIdWorker(String idWorker);
//    @Query("select im from Impression im where im.idImpression.idWorker= :idWorker")
//    List<Object[]>  findImpressionsByIdWorker(@Param("idWorker") String idWorker);
}
