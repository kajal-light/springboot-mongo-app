package com.kajal.spring.service.test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;

import com.kajal.spring.dto.DepartmentDTO;
import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.dto.SubjectDTO;
import com.kajal.spring.entity.Department;
import com.kajal.spring.entity.Student;
import com.kajal.spring.entity.Subject;
import com.kajal.spring.exception.InvalidEmailException;
import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.repository.DepartmentRepository;
import com.kajal.spring.repository.StudentRepository;
import com.kajal.spring.repository.SubjectRepository;
import com.kajal.spring.service.StudentServiceImpl;

public class StudentServiceImplTest {

	@Mock
	StudentRepository studentRepository;

	@Mock
	MongoOperations mongoOperation;

	@Mock
	SubjectRepository subjectRepository;

	@Mock
	DepartmentRepository departmentRepository;

	@InjectMocks
	StudentServiceImpl studentServiceImpl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void studentShouldGetCreated() throws StudentExistsException, InvalidEmailException {

		StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
		when(studentDTO.getEmail()).thenReturn("kps@gmail.com");

		DepartmentDTO departmentDTO = Mockito.mock(DepartmentDTO.class);

		when(departmentDTO.getDepartmentName()).thenReturn("CS");
		when(departmentDTO.getLocation()).thenReturn("Bhilai");
		when(studentDTO.getDepartment()).thenReturn(departmentDTO);
		SubjectDTO subjectDTO = Mockito.mock(SubjectDTO.class);
		List<SubjectDTO> sl = new ArrayList<>();
		when(subjectDTO.getId()).thenReturn("subjectID");
		when(subjectDTO.getMarkObtained()).thenReturn("34");
		when(subjectDTO.getSubjectName()).thenReturn("physics");
		sl.add(subjectDTO);
		when(studentDTO.getSubject()).thenReturn(sl);

		Student student = Mockito.mock(Student.class);
		when(student.getId()).thenReturn("studentId");
		when(studentRepository.save(Mockito.any())).thenReturn(student);
		Department department = Mockito.mock(Department.class);
		when(department.getId()).thenReturn("departmentId");
		when(student.getDepartment()).thenReturn(department);
		Subject subject = Mockito.mock(Subject.class);
		List<Subject> sl2 = new ArrayList<>();
		when(subject.getId()).thenReturn("subjectID");
		when(subjectRepository.saveAll(Mockito.anyList())).thenReturn(sl2);
		sl2.add(subject);
		when(student.getSubject()).thenReturn(sl2);

		studentServiceImpl.createStudent(studentDTO);

	}
	
	@Test(expected = InvalidEmailException.class)
	public void invalidEmailShouldNotCreateStudent() throws StudentExistsException, InvalidEmailException {
		StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
		when(studentDTO.getEmail()).thenReturn("kpsgmail.com");
		
		studentServiceImpl.createStudent(studentDTO);
	}
	
	@Test(expected = StudentExistsException.class)
	public void studentAlreadyExists_createStudent() throws StudentExistsException, InvalidEmailException {
		StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
		List<StudentDTO> sl = new ArrayList<>();
		sl.add(studentDTO);
		List<Student> studentList = new ArrayList<>();
		Student student = Mockito.mock(Student.class);
		when(student.getId()).thenReturn("studentId");
		when(studentDTO.getEmail()).thenReturn("kps@gmail.com");
		studentList.add(student);
		Query query = Mockito.mock(Query.class);
		Criteria criteria = Mockito.mock(Criteria.class);
		String fieldName = "email";
		when(studentRepository.findStudentByTag(mongoOperation, query, fieldName, studentDTO.getEmail())).thenReturn(studentList);
		when(studentServiceImpl.findStudentByGmail(fieldName, studentDTO.getEmail())).thenReturn(sl);
		
		studentServiceImpl.createStudent(studentDTO);
		
	}

}
