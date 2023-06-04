package com.example.workermicroservice.Projections;


import com.example.workermicroservice.Entities.worker.Worker;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.expression.spel.ast.Projection;
import org.springframework.data.rest.core.config.Projection;


@Projection(name="toWorker", types= Worker.class)
public interface WorkerProjection {




    @Value("#{target.firstName}")
    String getFirstName();
    @Value("#{target.lastName}")
    String getLastName();
    @Value("#{target.userId}")
    String getId();
//    @Value("#{target.}")
//    String getId();
//    @Value("#{target.}")
//    String getCreatedAt();
}