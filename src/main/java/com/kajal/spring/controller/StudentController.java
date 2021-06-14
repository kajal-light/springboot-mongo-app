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

import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.entity.Student;
import com.kajal.spring.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping("/create")
	public StudentDTO createStudent(@RequestBody StudentDTO student) {

		return studentService.createStudent(student);
	}

	@GetMapping("/getById/{id}")
	
	
	public StudentDTO getStudentbyId(@PathVariable String id) {

		return studentService.getStudentbyId(id);
	}

	@GetMapping("/all")
	public List<StudentDTO> getAllStudent() {
		return studentService.getAllStudent();

	}

	@PutMapping("/update")
	public String updateStudent(@RequestBody StudentDTO student) {

		return studentService.updateStudent(student);

	}

	@DeleteMapping("/delete/{id}")
	public String deleteStudent(@PathVariable String id) {

		return studentService.deleteStudent(id);

	}

	@GetMapping("/studentByname/{name}")
	public List<StudentDTO> studentByname(@PathVariable String name) {

		return studentService.getStudentBynName(name);
	}

	@GetMapping("/studentBynameANDMail")
	public List<StudentDTO> studentBynameANDMail(@RequestParam String name, @RequestParam String email) {

		return studentService.studentBynameANDMail(name, email);
	}

	@GetMapping("/studentBynameorMail")
	public List<StudentDTO> studentBynameORMail(@RequestParam String name, @RequestParam String email) {

		return studentService.studentBynameORMail(name, email);
	}

	@GetMapping("/allWithPagination")
	public List<Student> getallWithPagination(@RequestParam int page, @RequestParam int limit) {

		return studentService.getallWithPagination(page, limit);

	}

	@GetMapping("/getSort")
	public List<Student> getSort() {
		return studentService.getSort();

	}

	@GetMapping("/getdept")
	public List<StudentDTO> getbydept(@RequestParam String deptname) {

		return studentService.getbydept(deptname);

	}

	@GetMapping("/bySubject")
	public List<StudentDTO> studentBySubjectName(@RequestParam String subject) {

		return studentService.studentBySubjectName(subject);

	}

	@GetMapping("/byGmail")
	public List<StudentDTO> studentByGmail(@RequestParam String email) {
		String fieldName = "email";
		return studentService.findStudentByGmail(fieldName, email);

	}

	@GetMapping("/NameStartsWith")
	public List<StudentDTO> nameStartWith(@RequestParam String name) {

		return studentService.nameStartWith(name);

	}

	@GetMapping("/byDeptId")
	public List<Student> getByDptId(@RequestParam String deptId) {

		return studentService.getByDptId(deptId);

	}

}
