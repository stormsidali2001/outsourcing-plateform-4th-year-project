package com.example.authmicroservice.Repository;


import com.example.authmicroservice.Entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,Long> {
}