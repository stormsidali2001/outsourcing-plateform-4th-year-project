package com.example.workermicroservice.repositpries;

import com.example.workermicroservice.Entities.skill.Skill;
import com.example.workermicroservice.Entities.worker.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends MongoRepository<Skill,String> {
}
