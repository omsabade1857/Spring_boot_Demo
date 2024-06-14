package com.example.demo.services.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.AddressDto;
import com.example.demo.Dto.StudentDto;
import com.example.demo.entity.Address;
import com.example.demo.entity.Student;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.services.AddressService;
import com.example.demo.services.StudentService;
//import com.example.demo.repository.StudentRepository;
import com.example.demo.exception.EmptyResultException;
import com.example.demo.exception.ResourseNotFound;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StudentDto createStudent(StudentDto studentDto) {

		Student student = modelMapper.map(studentDto, Student.class);
		if (student == null) {
			throw new NullPointerException("Student is Empty!! please fill correct data ");
		}
		// save the student data
		List<Address> addressList = studentDto.getAddress().stream().map(addressDto -> {
			Address address = modelMapper.map(addressDto, Address.class);
			address.setStudent(student);
			return address;
		}).collect(Collectors.toList());

		// to check addressList data
		if (addressList.isEmpty()) {
			throw new NullPointerException("Please enter Address data!!");
		}

		// to check Address are properly save for this Student or not
		for (Address address : student.getAddress()) {
			System.out.println("Address to be saved: " + address.getStudent().getName());
		}

		student.setAddress(addressList);

		Student savedStudent = studentRepository.save(student);
		return modelMapper.map(savedStudent, StudentDto.class);
	}

	@Override
	public StudentDto getStudentById(int id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFound("Student not found with id " + id));

		return modelMapper.map(student, StudentDto.class);
	}

	// fetch data by name
	@Override
	public List<StudentDto> getStudentByName(String name) {
		List<Student> student = studentRepository.findByNameContainingIgnoreCase(name);
		if (student != null && !student.isEmpty()) {
			return student.stream()
					.map(s -> modelMapper.map(s, StudentDto.class)).toList();
		}
		throw new EmptyResultException("Student with this name not found :" + name);

	}

	// fetch data by role
	@Override
	public List<StudentDto> getStudentByDesignation(String designation) {
		List<Student> designation2 = studentRepository.findByDesignationIgnoreCase(designation);
		if (designation2 == null && designation2.isEmpty()) {
			throw new ResourseNotFound("Student with this role not found :" + designation);
		}
		return designation2.stream()
				.map(s -> modelMapper.map(s, StudentDto.class)).toList();
	}

	@Override
	public List<StudentDto> getAllStudent() {
		List<Student> students = studentRepository.findAll();
		if (students != null) {
			return students.stream()
					.map(s -> modelMapper.map(s, StudentDto.class)).toList();
		}
		throw new ResourseNotFound("Students not found :");
	}

	@Override
	public StudentDto updateStudentById(int id, StudentDto studentDto) {
		Student students = studentRepository.findById(id)
				.orElseThrow(() -> new ResourseNotFound("Student not found with id " + id));
		students.setName(studentDto.getName());
		students.setDesignation(studentDto.getDesignation());
		students.setDateOfBirth(studentDto.getDateOfBirth());
		students.setMobileNumber(studentDto.getMobileNumber());

		Student student2 = students;

		// Address still exist in list...I added this if I want to change only Student field
		if (studentDto.getAddress().isEmpty() && studentDto.getAddress() == null) {
			studentRepository.save(student2);
			System.out.println("Address are empty & you want only update Student");
		} else {
			System.out.println("request" + studentDto.getAddress());
			// address.getStudents().clear(); //clear existing student data
			System.out.println("responce" + students.getAddress());

			List<Address> addressList = studentDto.getAddress().stream()
					.map(addressDto -> {
				Address address = modelMapper.map(addressDto, Address.class);

				address.setStudent(students);
				return address;
			}).toList();

			List<Address> existingAddresses = new ArrayList<>(students.getAddress());

			// Update existing students or add new ones....I take help from chatGpt here
			for (Address newAddress : addressList) {
				boolean AddressExists = false;
				for (Address existingAddress : existingAddresses) {
					if (existingAddress.getId() == newAddress.getId()) {

						existingAddress.setState(newAddress.getState());
						existingAddress.setStreet(newAddress.getStreet());
						existingAddress.setPinCode(newAddress.getPinCode());
						AddressExists = true;
						break;
					}
				}
				if (!AddressExists) {
					System.out.println("New address created for student: " + newAddress.getStudent().getName());

					// adding new address into address list....IMPORTANT step
					students.getAddress().add(newAddress);
				}
			}


			students.setAddress(existingAddresses); // I take help here because UnsupportedOperationException
			
			student2 = studentRepository.save(students);
		}
		return modelMapper.map(student2, StudentDto.class);

	}

	@Override
	public void deleteStudentById(int id) {
		if (studentRepository.findById(id) != null) {
			studentRepository.deleteById(id);
		} else {
			throw new ResourseNotFound("Student not found with id " + id);
		}
	}

}