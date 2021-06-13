package com.kajal.spring.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "subject")

public class Subject {
	@Id
	private String id;
	@Field(name = "subject_name")
	private String subjectName;
	@Field(name = "mark_obtained")
	private String markObtained;

}
