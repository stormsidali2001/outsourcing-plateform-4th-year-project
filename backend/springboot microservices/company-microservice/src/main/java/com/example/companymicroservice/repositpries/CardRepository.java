package com.example.companymicroservice.repositpries;

import com.example.companymicroservice.Entities.company.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends MongoRepository<Card,String> {
    Card findCardByCompanyId(String idCompany);
}
