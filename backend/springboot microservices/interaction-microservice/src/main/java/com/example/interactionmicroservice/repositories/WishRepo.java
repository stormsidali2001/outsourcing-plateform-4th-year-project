package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Wish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepo extends JpaRepository<Wish, String> {
    List<Wish> findWishesByIdWorker(String idWorker);

}
