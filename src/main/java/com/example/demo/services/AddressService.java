package com.example.demo.services;

import java.util.List;

import com.example.demo.Dto.AddressDto;

public interface AddressService {
	public AddressDto createAddress(AddressDto addressDto);

	public AddressDto getAddressById(int id);
	//find address by state name
	public List<AddressDto> getAddressByState(String state);
	
	public List<AddressDto> getAllAddress();
	public AddressDto updateAddressById(int id, AddressDto addressDto);
	public void deleteAddressById(int id);
}
