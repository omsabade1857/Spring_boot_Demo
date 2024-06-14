package com.example.demo.services;

import java.util.List;

import com.example.demo.Dto.AddressDto;
import com.example.demo.Dto.StudentDto;

public interface StudentService {

	public StudentDto createStudent(StudentDto studentDto);

	public StudentDto getStudentById(int id);

	// find student by name
	public List<StudentDto> getStudentByName(String name);

	// find student by designation
	public List<StudentDto> getStudentByDesignation(String designation);

	// find all student
	public List<StudentDto> getAllStudent();

	public StudentDto updateStudentById(int id, StudentDto studentDto);

	public void deleteStudentById(int id);

}
