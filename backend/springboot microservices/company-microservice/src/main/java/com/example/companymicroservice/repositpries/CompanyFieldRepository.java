package com.example.companymicroservice.repositpries;

import com.example.companymicroservice.Entities.company.Company;
import com.example.companymicroservice.Entities.companyField.CompanyField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyFieldRepository extends MongoRepository<CompanyField,String> {
    Optional<CompanyField> findByName(String name);
}
