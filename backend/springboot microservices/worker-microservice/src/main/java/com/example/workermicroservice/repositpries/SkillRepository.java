package com.example.workermicroservice.repositpries;

import com.example.workermicroservice.Entities.worker.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends MongoRepository<Skill,String> {
    List<com.example.workermicroservice.Entities.skill.Skill> findAllByNameIn(List<String> names);
}
