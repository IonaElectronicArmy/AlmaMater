package com.rosalind.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
	@JsonProperty("studentId")
	private long studentId;
	
	@JsonProperty("studentName")
	private String studentName;
	
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	

}
