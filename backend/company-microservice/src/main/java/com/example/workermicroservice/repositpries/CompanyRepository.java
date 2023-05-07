package com.example.workermicroservice.repositpries;

import com.example.workermicroservice.Entities.company.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company,String> {
}
