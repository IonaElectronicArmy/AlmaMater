package com.iona.student.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.iona.student.controller.StudentController;
import com.iona.student.dto.Student;
import com.iona.student.manager.StudentManager;

@Component
public class StudentControllerImpl implements StudentController{

	@Autowired
	StudentManager studentmanager;
	
	@Override
	public ResponseEntity<Student> getStudentDetails(Long studentId) {
		Student student = studentmanager.getStudentDetails(studentId);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}

}
