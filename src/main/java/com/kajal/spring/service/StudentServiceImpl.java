package com.kajal.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.dto.SubjectDTO;
import com.kajal.spring.entity.Department;
import com.kajal.spring.entity.Student;
import com.kajal.spring.entity.Subject;
import com.kajal.spring.repository.DepartmentRepository;
import com.kajal.spring.repository.StudentRepository;
import com.kajal.spring.repository.SubjectRepository;

@Component
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	MongoOperations mongoOperation;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	public StudentDTO createStudent(StudentDTO student) {
		Student stu = new Student();
		if (!findStudentByGmail("email", student.getEmail()).isEmpty()) {
			return new StudentDTO();
		}
		Department department = new Department();
		List<Subject> subjectList = new ArrayList<>();
		if (student.getDepartment() != null) {

			department.setDepartmentName(student.getDepartment().getDepartmentName());
			department.setLocation(student.getDepartment().getLocation());
			departmentRepository.save(department);
		}

		if (student.getSubject() != null && !student.getSubject().isEmpty()) {

			for (SubjectDTO subjectDTO : student.getSubject()) {
				Subject subject = new Subject();
				subject.setSubjectName(subjectDTO.getSubjectName());
				subject.setMarkObtained(subjectDTO.getMarkObtained());
				subjectList.add(subject);
			}
			subjectList = subjectRepository.saveAll(subjectList);
		}

		stu.setEmail(student.getEmail());
		stu.setDepartment(department);
		stu.setName(student.getName());
		stu.setSubject(subjectList);
		stu = studentRepository.save(stu);
		student.setId(stu.getId());
		student.getDepartment().setId(stu.getDepartment().getId());
		for (int i = 0; i < subjectList.size(); i++) {
			student.getSubject().get(i).setId(subjectList.get(i).getId());
		}

		return student;

	}

	public Student getStudentbyId(String id) {

		return studentRepository.findById(id).orElse(new Student());
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

		Pageable pageable = PageRequest.of(page - 1, limit);
		return studentRepository.findAll(pageable).getContent();

	}

	public List<Student> getSort() {
		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		return studentRepository.findAll(sort);
	}

	public List<Student> getbydept(String deptname) {

		return studentRepository.findByDepartmentName(deptname);
	}

	public List<Student> studentBySubjectName(String subject) {

		return studentRepository.findBySubjectName(subject);
	}

	public List<Student> findStudentByGmail(String fieldName, String tag) {

		Query query = new Query();

		return studentRepository.findStudentByTag(mongoOperation, query, fieldName, tag);

	}

	public List<Student> nameStartWith(String name) {

		return studentRepository.findByNameStartsWith(name);
	}

	public List<Student> getByDptId(String deptId) {

		return studentRepository.findByDepartmentId(deptId);
	}

}
