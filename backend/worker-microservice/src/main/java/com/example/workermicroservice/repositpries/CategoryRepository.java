package com.example.workermicroservice.repositpries;

import com.example.workermicroservice.Entities.category.Category;
import com.example.workermicroservice.Entities.skill.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
    List<Category> findAllByNameIn(List<String> name);
}
