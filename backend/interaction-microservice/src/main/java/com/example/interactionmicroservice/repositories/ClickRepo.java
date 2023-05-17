package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Click;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClickRepo extends JpaRepository<Click, String> {
    List<Click> findClicksByIdWorker(String idWorker);
}
