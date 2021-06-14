package com.kajal.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.entity.Student;

@Service
public interface StudentService {

	StudentDTO createStudent(StudentDTO student);

	StudentDTO getStudentbyId(String id);

	List<StudentDTO> getAllStudent();

	String updateStudent(StudentDTO student);

	String deleteStudent(String id);

	List<StudentDTO> getStudentBynName(String name);

	List<StudentDTO> studentBynameANDMail(String name, String email);

	List<StudentDTO> studentBynameORMail(String name, String email);

	List<Student> getallWithPagination(int page, int limit);

	List<Student> getSort();

	List<StudentDTO> getbydept(String deptname);

	List<StudentDTO> studentBySubjectName(String subject);

	List<StudentDTO> findStudentByGmail(String fieldName, String tag);

	List<StudentDTO> nameStartWith(String name);

	List<Student> getByDptId(String deptId);

}
