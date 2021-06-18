package com.kajal.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.kajal.spring.dto.DepartmentDTO;
import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.dto.SubjectDTO;
import com.kajal.spring.entity.Department;
import com.kajal.spring.entity.Student;
import com.kajal.spring.entity.Subject;
import com.kajal.spring.exception.InvalidEmailException;
import com.kajal.spring.exception.NoDepartmentfound;
import com.kajal.spring.exception.NoSuchNameException;
import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.exception.StudentNotFoundException;
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

	public StudentDTO createStudent(StudentDTO student) throws StudentExistsException, InvalidEmailException {

		if (!validEmail(student.getEmail())) {
			throw new InvalidEmailException(HttpStatus.PRECONDITION_FAILED, "Given Email is invalid");
		}

		if (!findStudentByGmail("email", student.getEmail()).isEmpty()) {
			// if there is any record for a given email then we should throw
			// StudentExistsException
			throw new StudentExistsException(HttpStatus.NOT_ACCEPTABLE, "Student with the given email already exists!");
		}
		Student stu = new Student();
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

	private boolean validEmail(String email) {
		String emailRegex = "^(.+)@(.+)$";
		return Pattern.matches(emailRegex, email);

	}

	public StudentDTO getStudentbyId(String id) throws StudentNotFoundException {

		Optional<Student> studentEntity = studentRepository.findById(id);
		if (!studentEntity.isPresent()) {
			// if there is any record for a given email then we should throw
			// StudentExistsException
			throw new StudentNotFoundException(HttpStatus.NOT_FOUND, "student with is id not found");
		}
		Student stu = studentEntity.get();

		StudentDTO student = new StudentDTO();

		student.setId(stu.getId());
		student.setName(stu.getName());
		student.setEmail(stu.getEmail());
		DepartmentDTO departmentDto = new DepartmentDTO();
		departmentDto.setDepartmentName(stu.getDepartment().getDepartmentName());

		departmentDto.setId(stu.getDepartment().getId());
		departmentDto.setLocation(stu.getDepartment().getLocation());
		student.setDepartment(departmentDto);
		List<SubjectDTO> subjectList = new ArrayList<>();

		for (Subject subject : stu.getSubject()) {
			SubjectDTO subjectDTO = new SubjectDTO();

			subjectDTO.setId(subject.getId());
			subjectDTO.setSubjectName(subject.getSubjectName());
			subjectDTO.setMarkObtained(subject.getMarkObtained());

			subjectList.add(subjectDTO);
		}
		student.setSubject(subjectList);

		return student;

	}

	public List<StudentDTO> getAllStudent() {


		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> stuList = studentRepository.findAll();

		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;
	}

	public String updateStudent(StudentDTO student) {
		Optional<Student> studentDb = studentRepository.findById(student.getId());
		if (studentDb.isPresent()) {
			Student stu = studentDb.get();
			Department department = new Department();
			List<Subject> subjectList = new ArrayList<>();
			if (student.getDepartment() != null) {
				department.setId(student.getDepartment().getId());
				department.setDepartmentName(student.getDepartment().getDepartmentName());
				department.setLocation(student.getDepartment().getLocation());
				departmentRepository.save(department);
			}

			if (student.getSubject() != null && !student.getSubject().isEmpty()) {
				for (SubjectDTO subjectDTO : student.getSubject()) {
					Subject subject = new Subject();
					
					subject.setId(subjectDTO.getId());
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
			studentRepository.save(stu);
			return "record updated";
		} else {
			return "record does not exists";
		}
	}

	public String deleteStudent(String id) throws StudentNotFoundException {

		

		Optional<Student> studentEntity = studentRepository.findById(id);
		if (!studentEntity.isPresent()) {
			// if there is any record for a given email then we should throw
			// StudentExistsException
			throw new StudentNotFoundException(HttpStatus.NON_AUTHORITATIVE_INFORMATION,
					"student with is id not found");
		}

		studentRepository.deleteById(id);

		return "deleted";
	}

	public List<StudentDTO> getStudentByName(String name) throws NoSuchNameException {


		List<Student> stuList = studentRepository.findByName(name);

		if (stuList.isEmpty()) {

			throw new NoSuchNameException(HttpStatus.NO_CONTENT, "no such user found sorry");

		}

		List<StudentDTO> studentdtoList = new ArrayList<>();

		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> findStudentByNameAndMail(String name, String email) {


		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> stuList = studentRepository.findByNameAndEmail(name, email);
		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> findStudentByNameOrMail(String name, String email) {


		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> stuList = studentRepository.findByNameOrEmail(name, email);
		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> getAllWithPagination(int page, int limit) {
		List<StudentDTO> studentDTOList = new ArrayList<>();

		Pageable pageable = PageRequest.of(page - 1, limit);

		List<Student> studentPage = studentRepository.findAll(pageable).getContent();

		for (Student student : studentPage) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentDTOList.add(studentDTO);
		}

		return studentDTOList;

	}

	public List<StudentDTO> getSort() {

		Sort sort = Sort.by(Sort.Direction.ASC, "name");

		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> studentpage = studentRepository.findAll(sort);

		for (Student student : studentpage) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();

			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> getByDept(String deptName) throws NoDepartmentfound {

		List<Student> stuList = studentRepository.findByDepartmentName(deptName);
		
		if(stuList.isEmpty()) {
			
			throw new NoDepartmentfound(HttpStatus.EXPECTATION_FAILED,
					"Department not found");
			
		}
		
		
		
		
		List<StudentDTO> studentdtoList = new ArrayList<>();

		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> studentBySubjectName(String subject) {

		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> stuList = studentRepository.findBySubjectName(subject);

		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> findStudentByGmail(String fieldName, String tag) {

		Query query = new Query();
		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> stuList = studentRepository.findStudentByTag(mongoOperation, query, fieldName, tag);
		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<StudentDTO> nameStartWith(String name) {

		List<StudentDTO> studentdtoList = new ArrayList<>();

		List<Student> stuList = studentRepository.findByNameStartsWith(name);

		for (Student student : stuList) {

			StudentDTO studentDTO = new StudentDTO();
			studentDTO.setEmail(student.getEmail());
			studentDTO.setId(student.getId());
			studentDTO.setName(student.getName());

			DepartmentDTO departmentDto = new DepartmentDTO();
			departmentDto.setDepartmentName(student.getDepartment().getDepartmentName());
			departmentDto.setId(student.getDepartment().getId());
			departmentDto.setLocation(student.getDepartment().getLocation());
			studentDTO.setDepartment(departmentDto);

			List<SubjectDTO> subjectDtoList = new ArrayList<>();
			for (int i = 0; i < student.getSubject().size(); i++) {
				SubjectDTO subjectDTO = new SubjectDTO();
				subjectDTO.setId(student.getSubject().get(i).getId());
				subjectDTO.setSubjectName(student.getSubject().get(i).getSubjectName());
				subjectDTO.setMarkObtained(student.getSubject().get(i).getMarkObtained());

				subjectDtoList.add(subjectDTO);
			}
			studentDTO.setSubject(subjectDtoList);
			studentdtoList.add(studentDTO);
		}

		return studentdtoList;

	}

	public List<Student> getByDptId(String deptId) {

		return studentRepository.findByDepartmentId(deptId);
	}

}
