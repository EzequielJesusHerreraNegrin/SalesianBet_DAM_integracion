package com.accesodatos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.mapper.userentity.UserEntityMapper;
import com.accesodatos.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserEntityServiceImpl implements UserEntityService{

	@Autowired UserEntityMapper entityMapper;
	@Autowired UserRepository userRepository;
	
	@Override
	public List<UserEntityResponseDto> getAllUsers() {
		List<UserEntity> users = userRepository.findAll();
		return users.stream().map(entityMapper:: toUserEntityResponseDto).toList();
	}
}
