package com.example.companymicroservice.repositpries;

import com.example.companymicroservice.Entities.company.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company,String> {
    Optional<Company> findByName(String name);

    Optional<Company> findByUserId(String userId);

    @Query("SELECT c.userId FROM Company")
    List<String> findAllUserIds();

}
