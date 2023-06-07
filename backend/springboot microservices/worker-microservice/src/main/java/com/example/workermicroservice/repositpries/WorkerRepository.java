package com.example.workermicroservice.repositpries;
import com.example.workermicroservice.Entities.worker.Worker;
import com.example.workermicroservice.Projections.WorkerProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends MongoRepository<Worker,String> {

    List<Optional<Worker>> findAllByUserIdIn(List<String> userIds);

    @Query("SELECT w.userId FROM Worker w")
    List<String> findAllUserIds();

    Worker findWorkerByUserId(String userId);


    Boolean existsWorkerByUserId(String userId);


    @Query(value = "{}", fields = "{ 'firstName' : 1, 'lastName' : 1,'userId': 1}")
    List<WorkerProjection> getWorkers();



}
