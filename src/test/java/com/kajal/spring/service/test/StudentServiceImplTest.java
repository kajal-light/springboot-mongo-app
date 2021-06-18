package com.kajal.spring.service.test;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.kajal.spring.dto.DepartmentDTO;
import com.kajal.spring.dto.StudentDTO;
import com.kajal.spring.dto.SubjectDTO;
import com.kajal.spring.entity.Department;
import com.kajal.spring.entity.Student;
import com.kajal.spring.entity.Subject;
import com.kajal.spring.exception.InvalidEmailException;
import com.kajal.spring.exception.NoSuchNameException;
import com.kajal.spring.exception.StudentExistsException;
import com.kajal.spring.exception.StudentNotFoundException;
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

		sl2.add(subject);
		when(subjectRepository.saveAll(Mockito.anyList())).thenReturn(sl2);

		when(student.getSubject()).thenReturn(sl2);

		studentServiceImpl.createStudent(studentDTO);

	}

	@Test(expected = InvalidEmailException.class)
	public void invalidEmailShouldNotCreateStudent() throws StudentExistsException, InvalidEmailException {
		StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
		when(studentDTO.getEmail()).thenReturn("kpsgmail.com");

		studentServiceImpl.createStudent(studentDTO);
	}

	@Ignore
	@Test(expected = StudentExistsException.class)
	public void studentAlreadyExists_createStudent() throws StudentExistsException, InvalidEmailException {
		StudentDTO studentDTO = Mockito.mock(StudentDTO.class);
		List<StudentDTO> sl = new ArrayList<>();
		sl.add(studentDTO);
		List<Student> studentList = new ArrayList<>();
		Student student = Mockito.mock(Student.class);

		when(student.getId()).thenReturn("studentId");
		// when(studentDTO.getEmail()).thenReturn("kps@gmail.com");
		studentList.add(student);

		Query query = Mockito.mock(Query.class);
		Criteria criteria = Mockito.mock(Criteria.class);
		String fieldName = "email";
		when(studentRepository.findStudentByTag(mongoOperation, query, fieldName, studentDTO.getEmail()))
				.thenReturn(studentList);
		when(studentServiceImpl.findStudentByGmail(fieldName, studentDTO.getEmail())).thenReturn(sl);

		when(student.getEmail()).thenReturn("kps@gmail.com");
		when(student.getId()).thenReturn("Studentid");

		studentServiceImpl.createStudent(studentDTO);

	}

	@Test(expected = StudentNotFoundException.class)
	public void studentShouldNotGetById() throws StudentNotFoundException {
		String id = "studentId";
		studentServiceImpl.getStudentbyId(id);
	}

	@Test
	public void studentShouldGetById() throws StudentNotFoundException {
		String id = "studentId";
		Student stu = Mockito.mock(Student.class);

		when(stu.getId()).thenReturn("studentId");

		Department department = Mockito.mock(Department.class);
		when(department.getId()).thenReturn("departmentId");
		when(stu.getDepartment()).thenReturn(department);

		Subject subject = Mockito.mock(Subject.class);
		List<Subject> sl2 = new ArrayList<>();
		when(subject.getId()).thenReturn("subjectID");
		when(subject.getSubjectName()).thenReturn("maths");
		when(subject.getMarkObtained()).thenReturn("70");
		sl2.add(subject);
		when(stu.getSubject()).thenReturn(sl2);
		Optional<Student> studentEntity = Optional.of(stu);
		when(studentRepository.findById(Mockito.anyString())).thenReturn(studentEntity);
		studentServiceImpl.getStudentbyId(id);
	}

	@Test
	
	public void getAllStudent() {
		

		List<StudentDTO> studentdtoList = new ArrayList<>();	
		
		List<Student> stuList=new ArrayList<>();
		Student student= Mockito.mock(Student.class);
		
		when(student.getId()).thenReturn("studentId");

		Department department = Mockito.mock(Department.class);
		when(department.getId()).thenReturn("departmentId");
		when(student.getDepartment()).thenReturn(department);

		Subject subject = Mockito.mock(Subject.class);
		List<Subject> sl2 = new ArrayList<>();
		when(subject.getId()).thenReturn("subjectID");
		when(subject.getSubjectName()).thenReturn("maths");
		when(subject.getMarkObtained()).thenReturn("70");
		sl2.add(subject);
		when(student.getSubject()).thenReturn(sl2);
		
		stuList.add(student);
	
	
		when(studentRepository.findAll()).thenReturn(stuList);	
		
		
		studentServiceImpl.getAllStudent();
		
		
	}
	
	@Test
	public void souldUpdateStudent() {
		
		StudentDTO studentDTO=Mockito.mock(StudentDTO.class);
		
		Student stu=Mockito.mock(Student.class);
		DepartmentDTO department=Mockito.mock(DepartmentDTO.class);
		List<SubjectDTO> subjectList = new ArrayList<>();
		
		when(department.getId()).thenReturn("hdhd");
		when(department.getDepartmentName()).thenReturn("kajal");
		when(department.getLocation()).thenReturn("2344");		
		when(studentDTO.getDepartment()).thenReturn(department);
		
		SubjectDTO subjectDTO=Mockito.mock(SubjectDTO.class);
	//	Subject subject=Mockito.mock(Subject.class);
		
		when(subjectDTO.getSubjectName()).thenReturn("");
		when(subjectDTO.getId()).thenReturn("");
		when(subjectDTO.getMarkObtained()).thenReturn("");
		
		subjectList.add(subjectDTO);

		
	
//		when(subjectRepository.saveAll(Mockito.anyList())).thenReturn(subjectList);
		when(studentDTO.getSubject()).thenReturn(subjectList);
		
		

		
		Optional<Student> studentDb=Optional.of(stu);
		when(studentRepository.findById(Mockito.any())).thenReturn(studentDb);	
		
		studentServiceImpl.updateStudent(studentDTO);
	}
	
	@Test
	
	public void shouldNotUpdateStudent() {
		Student stu=Mockito.mock(Student.class);
		StudentDTO studentDTO=Mockito.mock(StudentDTO.class);
		
		Optional<Student> studentDb=Optional.of(stu);
		
		
		
		studentServiceImpl.updateStudent(studentDTO);
	}
	
	@Test
	
	public void studentShouldDelete() throws StudentNotFoundException {
		
		String id="john";
		
		Student stu=Mockito.mock(Student.class);
		
	
		
		Optional<Student> studentEntity=Optional.of(stu);
		
		when(studentRepository.findById(Mockito.any())).thenReturn(studentEntity);	
		
		studentServiceImpl.deleteStudent(id);
		
		
}
	
	@Test(expected = StudentNotFoundException.class)
	
	public void studentShouldNotFound() throws StudentNotFoundException {
		String id="john";
		Student stu=Mockito.mock(Student.class);
		Optional<Student> studentEntity=Optional.of(stu);
		
		studentServiceImpl.deleteStudent(id);
		
	}
	
	@Test
	
	public void studentShouldGetByName() throws StudentNotFoundException, NoSuchNameException {
		//StudentDTO studentDTO =Mockito.mock(StudentDTO.class);
	
		
		
		List<Student> StudentList=new ArrayList<>();
		String name = "studentName";
		Student stu = Mockito.mock(Student.class);

		when(stu.getId()).thenReturn("studentId");

		Department department = Mockito.mock(Department.class);
		when(department.getId()).thenReturn("departmentId");
		when(stu.getDepartment()).thenReturn(department);

		Subject subject = Mockito.mock(Subject.class);
		List<Subject> sl2 = new ArrayList<>();
		when(subject.getId()).thenReturn("subjectID");
		when(subject.getSubjectName()).thenReturn("maths");
		when(subject.getMarkObtained()).thenReturn("70");
		sl2.add(subject);
		when(stu.getSubject()).thenReturn(sl2);
		
		StudentList.add(stu);
		when(studentRepository.findByName(Mockito.anyString())).thenReturn(StudentList);
		studentServiceImpl.getStudentByName(name);
		
	}
	
	@Test(expected=NoSuchNameException.class)
	
	public void studentShouldNotFound1() throws NoSuchNameException {
		
		String name = "studentName";
		Student stu = Mockito.mock(Student.class);
		
		

		List<Student> StudentList=new ArrayList<>();
		StudentList.add(stu);
		studentServiceImpl.getStudentByName(name);
		
	}
	
	@Test
	
	
	
	public void studentShouldGetByNameAndEmail() {
		
		

		
		List<Student> StudentList=new ArrayList<>();
		String mail="abshd";
		String name = "studentName";
		Student stu = Mockito.mock(Student.class);

		when(stu.getId()).thenReturn("studentId");

		Department department = Mockito.mock(Department.class);
		when(department.getId()).thenReturn("departmentId");
		when(stu.getDepartment()).thenReturn(department);

		Subject subject = Mockito.mock(Subject.class);
		List<Subject> sl2 = new ArrayList<>();
		when(subject.getId()).thenReturn("subjectID");
		when(subject.getSubjectName()).thenReturn("maths");
		when(subject.getMarkObtained()).thenReturn("70");
		sl2.add(subject);
		when(stu.getSubject()).thenReturn(sl2);
		
		StudentList.add(stu);
		when(studentRepository.findByNameAndEmail(name,mail)).thenReturn(StudentList);
		studentServiceImpl.findStudentByNameAndMail(name,mail);
		
		
		
		
		
		
	}
}
