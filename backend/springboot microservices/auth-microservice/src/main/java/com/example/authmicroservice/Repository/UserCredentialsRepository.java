package com.example.authmicroservice.Repository;


import com.example.authmicroservice.Entity.UserCredentials;
import com.example.authmicroservice.dto.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentials,String> {

    public Optional<UserCredentials> findByEmail(String email);



    @Query("SELECT COUNT(u) FROM UserCredentials u WHERE u.role = 'WORKER'")
    public int getWorkerCount();

    @Query("SELECT COUNT(u) FROM UserCredentials u WHERE u.role = 'ADMIN'")
    public int getAdminCount();

    @Query("SELECT COUNT(u) FROM UserCredentials u WHERE u.role = 'COMPANY'")
    public int getCompanyCount();

    @Query("SELECT COUNT(u) FROM UserCredentials u WHERE  u.role = 'WORKER' AND u.status = 'BANNED'")
    public int getBannedCount();



}
