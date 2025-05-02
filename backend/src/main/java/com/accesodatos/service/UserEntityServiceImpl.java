package com.accesodatos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.product.ProductResponseDto;
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

	@Autowired UserEntityRepository userEntityRepository;
	@Autowired UserEntityMapper entityMapper;
	
	@Override
	public List<UserEntityResponseDto> getAllUsers() {
		List<UserEntity> users = userEntityRepository.findAll();
		return users.stream().map(entityMapper::toUserEntityResponseDto).collect(Collectors.toList());
	}

	@Override
	public UserEntityResponseDto getUserByEmail(String email) {
		UserEntity user = userEntityRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format("No user with email: %s was found.", email)));
		return entityMapper.toUserEntityResponseDto(user);
	}

	@Override
	public boolean createUser(UserEntityRegisterRequestDto dto) {
		UserEntity user = new UserEntity();
		
		user.setUserName(dto.getUserName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setDni(dto.getDni());
		user.setCountry(dto.getCountry());
		System.out.println(user.getCountry());
		System.out.println(dto.getCountry());
		user = userEntityRepository.save(user);
		
		return user != null ;
	}

	@Override
	public List<ProductResponseDto> getUserBoughtProducts(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
