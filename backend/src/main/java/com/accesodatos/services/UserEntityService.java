package com.accesodatos.services;

import java.util.List;

import com.accesodatos.dto.userentity.UserEntityResponseDto;

public interface UserEntityService {

	List<UserEntityResponseDto> getAllUsers(); 
}
