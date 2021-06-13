package com.kajal.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.entity.Student;

@Service
public interface StudentService {

	StudentDTO createStudent(StudentDTO student);

	Student getStudentbyId(String id);

	List<Student> getAllStudent();

	Student updateStudent(Student student);

	String deleteStudent(String id);

	List<Student> getStudentBynName(String name);

	List<Student> studentBynameANDMail(String name, String email);

	List<Student> studentBynameORMail(String name, String email);

	List<Student> getallWithPagination(int page, int limit);

	List<Student> getSort();

	List<Student> getbydept(String deptname);

	List<Student> studentBySubjectName(String subject);

	List<Student> findStudentByGmail(String fieldName, String tag);

	List<Student> nameStartWith(String name);

	List<Student> getByDptId(String deptId);

}
