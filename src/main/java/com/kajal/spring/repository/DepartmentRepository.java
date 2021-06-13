package com.kajal.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kajal.spring.entity.Department;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

}
