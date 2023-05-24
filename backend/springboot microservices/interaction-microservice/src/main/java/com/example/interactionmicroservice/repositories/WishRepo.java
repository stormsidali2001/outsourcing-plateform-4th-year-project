package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Impression;
import com.example.interactionmicroservice.Entities.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepo extends JpaRepository<Wish, String> {

    List<Wish> findWishesByIdWorker(String idWorker);
    int countWishByIdWorker(String idWorker);
    @Query("SELECT i.idWorker, COUNT(i) AS wishCount FROM Wish i WHERE i.idWorker IN :workerIds GROUP BY i.idWorker")
    List<Object[]> getWishCountByWorkers(@Param("workerIds") List<String> workerIds);


}
