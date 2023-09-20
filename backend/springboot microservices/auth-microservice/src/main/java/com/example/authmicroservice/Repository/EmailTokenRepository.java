package com.example.authmicroservice.Repository;

import com.example.authmicroservice.Entity.EmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken,String> {

}
