package com.kajal.spring.repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.kajal.spring.entity.Student;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

	List<Student> findByName(String name);

	List<Student> findByNameAndEmail(String name, String email);

	@Query("{'department.department_name': ?0}")
	List<Student> findByDepartmentName(String deptname);

	List<Student> findByNameOrEmail(String name, String email);

	@Query("{'subject.subject_name': ?0}")
	List<Student> findBySubjectName(String subject);

	default List<Student> findStudentByTag(MongoOperations mongoOperation,
			org.springframework.data.mongodb.core.query.Query query, String fieldName, String tag) {

		query.addCriteria(Criteria.where(fieldName).regex(tag));

		return mongoOperation.find(query, Student.class);
	}
	
	List<Student> findByNameStartsWith(String name);
	
	List<Student> findByDepartmentId(String deptId);
	

	
}
