package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.dto.userentity.UserEntityRegisterRequestDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;

public interface UserEntityService {

	List<UserEntityResponseDto> getAllUsers();
	UserEntityResponseDto getUserByEmail(String email);

	UserEntityResponseDto createUser(UserEntityRegisterRequestDto dto);
	

}
