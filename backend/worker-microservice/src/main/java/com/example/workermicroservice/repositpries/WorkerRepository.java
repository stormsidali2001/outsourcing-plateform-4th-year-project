package com.example.workermicroservice.repositpries;
import com.example.workermicroservice.Entities.worker.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkerRepository extends MongoRepository<Worker,String> {
}
