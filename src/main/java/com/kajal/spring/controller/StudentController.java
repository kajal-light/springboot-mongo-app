package com.kajal.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kajal.spring.Service.StudentService;
import com.kajal.spring.entity.Student;

@RestController
@RequestMapping("/api/student")


public class StudentController {

	@Autowired
	StudentService studentService;
	
	@PostMapping("/create")
	public Student createStudent(@RequestBody Student student) {
		
		return studentService.createStudent(student);
		}
	
	@GetMapping("/getById/{id}")
	 public Student getStudentbyId(@PathVariable String id) {
		
		
		return studentService.getStudentbyId(id);
	}
	
	@GetMapping("/all")
	public List<Student> getAllStudent(){
		return studentService.getAllStudent();
		
	}
	@PutMapping("/update")	
	
	public Student updateStudent(@RequestBody Student student) {
		
		return studentService.updateStudent(student);
		
		
		
	}
	
	@DeleteMapping("/delete/{id}")
	 public String deleteStudent(@PathVariable String id) {
		
		return studentService.deleteStudent(id);
		
	}
	@GetMapping("/studentByname/{name}")
	
	public List<Student> studentByname(@PathVariable String name){
		
		
		return studentService.getStudentBynName(name);
	}
	@GetMapping("/studentBynameANDMail")

	public List<Student> studentBynameANDMail(@RequestParam String name,@RequestParam String email){
		
		return studentService.studentBynameANDMail(name,email);
	}
	@GetMapping("/studentBynameorMail")

	public List<Student> studentBynameORMail(@RequestParam String name,@RequestParam String email){
		
		return studentService.studentBynameORMail(name,email);
	}
	@GetMapping("/allWithPagination")
	public List<Student> getallWithPagination(@RequestParam int page,@RequestParam int limit){
		
		return studentService.getallWithPagination(page,limit);
		
		
	}
	@GetMapping("/getSort")
	
	public List<Student> getSort(){
		return studentService.getSort();
		
		
	}
	
	@GetMapping("/getdept")
	 public List<Student> getbydept(@RequestParam String deptname){
		
		return studentService.getbydept(deptname);
		
	}
	
	}
	

