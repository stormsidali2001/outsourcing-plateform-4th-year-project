package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Impression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ImpressionRepo extends JpaRepository<Impression, String> {
    List<Impression> findImpressionsByIdWorker(String idWorker);
    int countImpressionByIdWorker(String idWorker);
    @Query("SELECT i.idWorker, COUNT(i) AS impressionCount FROM Impression i WHERE i.idWorker IN :workerIds GROUP BY i.idWorker")
    List<Object[]> getImpressionCountByWorkers(@Param("workerIds") List<String> workerIds);


}
