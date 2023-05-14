package com.example.interactionmicroservice.repositories;

import com.example.interactionmicroservice.Entities.Click;
import com.example.interactionmicroservice.types.InteractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepo extends JpaRepository<Click, InteractionId> {
}
