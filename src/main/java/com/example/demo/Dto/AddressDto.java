package com.example.demo.Dto;

import java.util.List;

import com.example.demo.entity.Student;

import jakarta.validation.Valid;
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
public class AddressDto {
	private int id;

	@NotEmpty(message = "State cannot be empty")
	@Pattern(regexp = "^[A-Za-z\\s-_]+$", message = "Enter only alphabets, spaces, '-'and '_' are allowed")
	private String state;

	@NotEmpty(message = "Street cannot be empty")
	@Pattern(regexp = "^[A-Za-z\\s-_]+$", message = "Enter only alphabets, spaces, '-'and '_' are allowed")
	private String street;

	@NotNull(message = "Pincode cannot be empty")
	// @Pattern(regexp = "^[0-9]+$", message = "Enter only numbers")//....not work
	// @Numeric(message = "Enter only numbers")
	private long pinCode;

	private int studentId;
}
