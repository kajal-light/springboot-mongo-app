package com.kajal.spring.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("departmentId")
	private String id;
	@JsonProperty("departmentName")
	private String departmentName;
	@JsonProperty("departmentLocation")
	private String location;

}
