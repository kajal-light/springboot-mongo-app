package com.kajal.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.entity.Student;
import com.kajal.spring.exception.StudentExistsException;

@Service
public interface StudentService {

	StudentDTO createStudent(StudentDTO student) throws StudentExistsException;

	StudentDTO getStudentbyId(String id);

	List<StudentDTO> getAllStudent();

	String updateStudent(StudentDTO student);

	String deleteStudent(String id);

	List<StudentDTO> getStudentBynName(String name);

	List<StudentDTO> studentBynameANDMail(String name, String email);

	List<StudentDTO> studentBynameORMail(String name, String email);

	List<StudentDTO> getallWithPagination(int page, int limit);

	List<StudentDTO> getSort();

	List<StudentDTO> getbydept(String deptname);

	List<StudentDTO> studentBySubjectName(String subject);

	List<StudentDTO> findStudentByGmail(String fieldName, String tag);

	List<StudentDTO> nameStartWith(String name);

	List<Student> getByDptId(String deptId);

}
