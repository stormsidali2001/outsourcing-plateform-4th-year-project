package com.example.companymicroservice.repositpries;

import com.example.companymicroservice.Entities.skill.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends MongoRepository<Skill,String> {
    List<Skill> findAllByNameIn(List<String> names);
}
