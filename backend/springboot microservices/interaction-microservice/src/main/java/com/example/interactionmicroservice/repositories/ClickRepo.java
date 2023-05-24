package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Click;
import com.example.interactionmicroservice.Entities.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickRepo extends JpaRepository<Click, String> {

    List<Wish> findClicksByIdWorker(String idWorker);
    int countClickByIdWorker(String idWorker);
    @Query("SELECT i.idWorker, COUNT(i) AS clickCount FROM Click i WHERE i.idWorker IN :workerIds GROUP BY i.idWorker")
    List<Object[]> getClickCountByWorkers(@Param("workerIds") List<String> workerIds);


}
