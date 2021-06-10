package com.kajal.spring.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "student")
public class Student {

	private String name;

	@Id
	private String id;

	@Field(name = "mail")
	private String email;
	private Department department;

	private List<Subject> subjects;

}
