package com.rosalind.student.manager;

import org.springframework.stereotype.Service;

import com.rosalind.student.dto.Student;

@Service
public class StudentManager {
	public Student getStudentDetails(Long studentId){
		Student student = new Student();
		student.setStudentId(studentId);
		student.setStudentName("Praphul");
		return student;
	}
}
