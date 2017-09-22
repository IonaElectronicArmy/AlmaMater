package com.iona.student.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iona.student.dto.Student;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/student")
public interface StudentController {
	@RequestMapping(value = "/getStudent", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "Returns single student details", notes = "Returns single student details", response = Student.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Test success", response = Student.class),
	    @ApiResponse(code = 404, message = "Failure"),
	    @ApiResponse(code = 500, message = "Internal server error")}
	)
	public ResponseEntity<Student> getStudentDetails(@PathVariable Long studentId);
}
