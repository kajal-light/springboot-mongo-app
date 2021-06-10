package com.kajal.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kajal.spring.entity.Student;
import com.kajal.spring.repository.StudentRepository;
@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepository;
	
	
	public Student createStudent(Student student) {
		
		
		
		return studentRepository.save(student);
	}


	public Student getStudentbyId(String id) {
		
		return studentRepository.findById(id).get();
	}


	public List<Student> getAllStudent() {
		
		return studentRepository.findAll();
	}


	public Student updateStudent(Student student) {
		return studentRepository.save(student);
		
	}


	public String deleteStudent(String id) {
		
		studentRepository.deleteById(id);
		return "deleted";
	}


	public List<Student> getStudentBynName(String name) {
		return studentRepository.findByName(name);	
		 
	}


	public List<Student> studentBynameANDMail(String name, String email) {
		
		return studentRepository.findByNameAndEmail(name, email);
	}


	public List<Student> studentBynameORMail(String name, String email) {
		
		return studentRepository.findByNameOrEmail(name, email);
	}


	public List<Student> getallWithPagination(int page, int limit) {
		
		
		Pageable pageable=PageRequest.of(page-1, limit);
		return studentRepository.findAll(pageable).getContent() ;
		

	}


	public List<Student> getSort() {
		Sort sort=Sort.by(Sort.Direction.ASC, "name");
		
		
		return studentRepository.findAll(sort);
	}


	public List<Student> getbydept(String deptname) {
		
		return studentRepository.findByDepartmentDepartmentName(deptname);
	}


}