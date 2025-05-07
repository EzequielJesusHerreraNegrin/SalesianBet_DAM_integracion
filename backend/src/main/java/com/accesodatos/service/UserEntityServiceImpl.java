package com.accesodatos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.userentity.UserEntityRegisterRequestDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.userentity.UserEntityMapper;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserEntityServiceImpl implements UserEntityService{

	@Autowired 
	UserEntityRepository userEntityRepository;
	
	@Autowired 
	UserEntityMapper userEntityMapper;
	
	@Override
	public List<UserEntityResponseDto> getAllUsers() {
		List<UserEntity> users = userEntityRepository.findAll();
		return users.stream().map(userEntityMapper::toUserEntityResponseDto).collect(Collectors.toList());
	}

	@Override
	public UserEntityResponseDto getUserByEmail(String email) {
		UserEntity user = userEntityRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format("No user with email: %t was found.", email)));
		return userEntityMapper.toUserEntityResponseDto(user);
	}

	@Override
	public UserEntityResponseDto createUser(UserEntityRegisterRequestDto dto) {
		UserEntity user = new UserEntity();
		
		user.setUserName(dto.getUserName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setDni(dto.getDni());
		user.setCountry(dto.getCountry());
		user.setPoints(100);
		
		user = userEntityRepository.save(user);
		
		return userEntityMapper.toUserEntityResponseDto(user) ;
	}

}
