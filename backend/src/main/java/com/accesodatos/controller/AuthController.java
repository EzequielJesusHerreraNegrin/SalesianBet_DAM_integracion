package com.accesodatos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.auth.AuthLoginRequestDto;
import com.accesodatos.dto.auth.AuthRegisterRequestDto;
import com.accesodatos.dto.auth.AuthResponseDto;
import com.accesodatos.service.impl.UserDetailsServiceImpl;


@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired UserDetailsServiceImpl detailsServiceImpl;
	
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<AuthResponseDto>> login(@RequestBody AuthLoginRequestDto dto) {
		AuthResponseDto user = detailsServiceImpl.login(dto);
		
		ApiResponseDto<AuthResponseDto> response = new ApiResponseDto<AuthResponseDto>("User login successfully", HttpStatus.ACCEPTED.value(), user);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> register(@RequestBody AuthRegisterRequestDto dto) {
		Boolean registered = detailsServiceImpl.register(dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("User registered successfully", HttpStatus.OK.value(), registered);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
