package com.example.authmicroservice.Repository;


import com.example.authmicroservice.Entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,String> {

    public Optional<UserCredentials> findByEmail(String email);
    @Query("SELECT u FROM UserCredentials u JOIN FETCH u.emailToken t WHERE u.email = :email")
    public Optional<UserCredentials> findByEmailWithToken(@Param("email") String email);



}
