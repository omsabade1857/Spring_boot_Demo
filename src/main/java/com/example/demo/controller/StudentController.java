package com.example.demo.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.AddressDto;
import com.example.demo.Dto.StudentDto;
import com.example.demo.services.AddressService;
import com.example.demo.services.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;
	
	@PostMapping("/")
	public ResponseEntity<StudentDto> addNewStudent(@Valid @RequestBody StudentDto studentDto) {
		logger.debug("Received request to create address: {}", studentDto);
		StudentDto studentDto2 = studentService.createStudent(studentDto);
		 logger.debug("Created Student: {}", studentDto2);
		return new ResponseEntity<>(studentDto2, HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public ResponseEntity<StudentDto> getStudent(@PathVariable("id") int id) {
		StudentDto studentDto = studentService.getStudentById(id);

		if (studentDto != null) {
			logger.debug("Found Student: {}", studentDto);
			return ResponseEntity.ok(studentDto);
		} else {
			logger.warn("Not found student: {}", studentDto);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/searchByName/{name}")
	public ResponseEntity<List<StudentDto>> getStudentByName(@PathVariable("name") String name) {
		logger.debug("Received request to get by Name ");
		List<StudentDto> studentDtos = studentService.getStudentByName(name);

		if (studentDtos != null && !studentDtos.isEmpty()) {
			logger.debug("Employees fetched successfully: {}", studentDtos);
			return new ResponseEntity<>(studentDtos, HttpStatus.OK);
		} else {
			logger.warn("Data is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/searchByRole/{name}")
	public ResponseEntity<List<StudentDto>> getStudentByDesignation(@PathVariable("name") String designation) {
		logger.debug("Received request to get by Designation ");
		List<StudentDto> studentDtos = studentService.getStudentByDesignation(designation);

		if (studentDtos != null && !studentDtos.isEmpty()) {
			logger.debug("Employees fetched successfully: {}", studentDtos);
			return new ResponseEntity<>(studentDtos, HttpStatus.OK);
		} else {
			logger.warn("Data is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<StudentDto>> getStudent() {
		logger.debug("Received request to get all addresses");
		List<StudentDto> studentDtos = studentService.getAllStudent();
		if (studentDtos != null) {
			logger.debug("Employee fetch sucessfully {} ", studentDtos);
			return new ResponseEntity<>(studentDtos, HttpStatus.ACCEPTED);
		} else {
			logger.warn("Data is empty");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StudentDto> updateStudentById(@PathVariable("id") int id,@Valid @RequestBody StudentDto studentDto){
		 logger.debug("Received request to update student with id: {}", id);
		StudentDto studentDto2=studentService.updateStudentById(id, studentDto);
		 if (studentDto2 != null) {
	            logger.debug("Updated student: {}", studentDto2);
	            return new ResponseEntity<>(studentDto2, HttpStatus.ACCEPTED);
	        } else {
	            logger.warn("student with id {} not found for update", id);
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") int id) {
		logger.debug("Delete Request: ", id);
		if(studentService.getStudentById(id)!=null) {
			studentService.deleteStudentById(id);
			logger.warn("Your data deleted by id {}",id);
			return ResponseEntity.status(HttpStatus.OK).body("Data Deleted !!");
		}
		logger.warn("Student not found by id {}",id);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address Id Required");
	}

}
