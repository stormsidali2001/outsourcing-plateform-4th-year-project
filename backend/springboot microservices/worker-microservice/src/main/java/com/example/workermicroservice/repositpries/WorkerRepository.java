package com.example.workermicroservice.repositpries;
import com.example.workermicroservice.Entities.worker.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkerRepository extends MongoRepository<Worker,String> {

    List<Optional<Worker>> findAllByUserIdIn(List<String> userIds);
}
