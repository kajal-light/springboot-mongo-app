package com.kajal.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.entity.Student;
import com.kajal.spring.exception.InvalidEmailException;
import com.kajal.spring.exception.NoDepartmentfound;
import com.kajal.spring.exception.NoSuchNameException;
import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.exception.StudentNotFoundException;

@Service
public interface StudentService {

	StudentDTO createStudent(StudentDTO student) throws StudentExistsException, InvalidEmailException;

	StudentDTO getStudentbyId(String id) throws StudentNotFoundException;

	List<StudentDTO> getAllStudent();

	String updateStudent(StudentDTO student);

	String deleteStudent(String id) throws StudentNotFoundException;

	List<StudentDTO> getStudentByName(String name)throws NoSuchNameException;

	List<StudentDTO> findStudentByNameAndMail(String name, String email);

	List<StudentDTO> findStudentByNameOrMail(String name, String email);

	List<StudentDTO> getAllWithPagination(int page, int limit);

	List<StudentDTO> getSort();

	List<StudentDTO> getByDept(String deptname) throws NoDepartmentfound;

	List<StudentDTO> studentBySubjectName(String subject);

	List<StudentDTO> findStudentByGmail(String fieldName, String tag);

	List<StudentDTO> nameStartWith(String name);

	List<Student> getByDptId(String deptId);

}
