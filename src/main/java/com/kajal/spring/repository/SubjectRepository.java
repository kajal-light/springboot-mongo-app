package com.kajal.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kajal.spring.entity.Subject;

@Repository
public interface SubjectRepository extends MongoRepository<Subject, String> {

}
