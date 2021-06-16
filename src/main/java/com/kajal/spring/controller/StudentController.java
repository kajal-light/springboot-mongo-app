package com.kajal.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.kajal.spring.exception.InvalidEmailException;
import com.kajal.spring.exception.NoDepartmentfound;
import com.kajal.spring.exception.NoSuchNameException;
import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.exception.StudentNotFoundException;
import com.kajal.spring.service.StudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping("/create")
	// return type ResponseEntity<StudentDTO>
	public StudentDTO createStudent(@RequestBody StudentDTO student) throws StudentExistsException, InvalidEmailException {

		try {
			return studentService.createStudent(student);
		} catch (StudentExistsException e) {
			throw new StudentExistsException(HttpStatus.NOT_ACCEPTABLE, "Student with the given email already exists!");
		} catch (InvalidEmailException e) {
			throw new InvalidEmailException(HttpStatus.PRECONDITION_FAILED, "Given Email is invalid");
		}
	}

	@GetMapping("/getById/{id}")

	public StudentDTO getStudentbyId(@PathVariable String id) throws StudentNotFoundException {

		try {

			return studentService.getStudentbyId(id);

		}

		catch (StudentNotFoundException e) {

			throw new StudentNotFoundException(HttpStatus.NOT_FOUND, "student with is id not found");

		}

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
	public String deleteStudent(@PathVariable String id) throws StudentNotFoundException {
		try {
			return studentService.deleteStudent(id);
		} catch (StudentNotFoundException e) {

			throw new StudentNotFoundException(HttpStatus.NON_AUTHORITATIVE_INFORMATION,
					"student with is id not found");

		}

	}

	@GetMapping("/studentByname/{name}")
	public List<StudentDTO> studentByname(@PathVariable String name) throws NoSuchNameException {
		try {
			return studentService.getStudentByName(name);
		} catch (NoSuchNameException e) {

			throw new NoSuchNameException(HttpStatus.BAD_REQUEST, "no such user found sorry");

		}

	}

	@GetMapping("/studentBynameANDMail")
	public List<StudentDTO> studentBynameANDMail(@RequestParam String name, @RequestParam String email) {

		return studentService.findStudentByNameAndMail(name, email);
	}

	@GetMapping("/studentBynameorMail")
	public List<StudentDTO> studentBynameORMail(@RequestParam String name, @RequestParam String email) {

		return studentService.findStudentByNameOrMail(name, email);
	}

	@GetMapping("/allWithPagination")
	public List<StudentDTO> getallWithPagination(@RequestParam int page, @RequestParam int limit) {

		return studentService.getAllWithPagination(page, limit);

	}

	@GetMapping("/getSort")
	public List<StudentDTO> getSort() {

		return studentService.getSort();

	}

	@GetMapping("/getdept")
	public List<StudentDTO> getbydept(@RequestParam String deptname) throws NoDepartmentfound  {

		try {
			return studentService.getByDept(deptname);
		} catch (NoDepartmentfound e) {
		
			throw new NoDepartmentfound(HttpStatus.EXPECTATION_FAILED,
					"Department not found");
			
		}

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
