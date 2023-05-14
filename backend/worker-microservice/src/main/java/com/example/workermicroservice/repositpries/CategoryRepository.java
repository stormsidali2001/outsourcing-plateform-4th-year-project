package com.example.companymicroservice.repositpries;

import com.example.companymicroservice.Entities.category.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    List<Category> findAllByNameIn(List<String> name);
}
