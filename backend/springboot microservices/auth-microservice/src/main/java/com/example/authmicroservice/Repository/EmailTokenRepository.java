package com.example.authmicroservice.Repository;

import com.example.authmicroservice.Entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken,String> {
    @Query("SELECT et FROM EmailToken et JOIN FETCH et.user u WHERE et.id = :tokenId")
    Optional<EmailToken> findByTokenId(@Param("tokenId") String tokenId);
}
