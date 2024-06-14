package com.example.demo.Dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.Address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentDto {
	private int id;

	@NotBlank
	@NotEmpty(message = "Name cannot be empty")
	@Pattern(regexp = "^[A-Za-z\\s-_]+$", message = "Enter correct format Name")
	private String name;

	@NotBlank
	@NotEmpty(message = "Designation cannot be empty")
	@Pattern(regexp = "^[A-Za-z\\s-_]+$", message = "Enter correct String value")
	private String designation;

	@NotNull(message = "Date of birth cannot be empty")
	private LocalDate dateOfBirth;

	@NotBlank(message = "Mobile number cannot be empty")
	@Pattern(regexp = "^\\d{10}$", message = "Enter 10 digit mobile number")
	private String mobileNumber;

	@Valid   //IMPORTANT step to validate Student Field
	private List<AddressDto> address;

}