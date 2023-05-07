package com.example.companymicroservice.repositpries;

import com.example.companymicroservice.Entities.company.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company,String> {
}
