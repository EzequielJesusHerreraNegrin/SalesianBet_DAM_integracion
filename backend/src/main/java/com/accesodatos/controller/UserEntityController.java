package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.services.UserEntityServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class UserEntityController {

	@Autowired UserEntityServiceImpl userEntityServiceImpl;
	
	@GetMapping("/users/ping")
	public ResponseEntity<String> pong() {
		return ResponseEntity.ok("pong USER...");
	}
	
	@GetMapping(value = "/users",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<ApiResponseDto<List<UserEntityResponseDto>>> getAllUsers() {
		List<UserEntityResponseDto> users = userEntityServiceImpl.getAllUsers();
		
		ApiResponseDto<List<UserEntityResponseDto>> response = new ApiResponseDto<List<UserEntityResponseDto>>("Users fetched sucessfuly.", HttpStatus.OK.value(), users);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
