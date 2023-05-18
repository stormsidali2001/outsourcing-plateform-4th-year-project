package com.example.interactionmicroservice.projections;

import com.example.interactionmicroservice.Entities.Impression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="toWorker", types= Impression.class)
public interface ImpressionToWorker {



    @Value("#{target.idCompany}")
    String getIdCompany();
    @Value("#{target.idWorker}")
    String getIdWorker();


    @Value("#{target.createdAt}")
    String getCreatedAt();
}
