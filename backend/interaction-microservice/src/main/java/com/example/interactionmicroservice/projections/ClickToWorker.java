package com.example.interactionmicroservice.projections;

import com.example.interactionmicroservice.Entities.Click;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="ClickToWorker", types= Click.class)
public interface ClickToWorker {

    @Value("#{target.idCompany}")
    String getIdCompany();
    @Value("#{target.idWorker}")
    String getIdWorker();


    @Value("#{target.createdAt}")
    String getCreatedAt();
}
