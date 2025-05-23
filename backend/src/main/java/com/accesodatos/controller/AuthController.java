package com.accesodatos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@Autowired UserDetailsServiceImpl detailsServiceImpl;
	
	/**
	 * This function handles a POST request for user login, returning a response with the user details.
	 * 
	 * @param dto The parameter `dto` in the `login` method is of type `AuthLoginRequestDto`. It is
	 * annotated with `@RequestBody`, indicating that the method expects the request body to be converted
	 * to this object. The `AuthLoginRequestDto` class likely contains the necessary information for user
	 * authentication,
	 * @return The method is returning a `ResponseEntity` object containing an `ApiResponseDto` with a
	 * success message "User login successfully", an HTTP status code of 202 (ACCEPTED), and the
	 * `AuthResponseDto` object representing the user who logged in.
	 */
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<AuthResponseDto>> login(@RequestBody AuthLoginRequestDto dto) {
		AuthResponseDto user = detailsServiceImpl.login(dto);
		
		ApiResponseDto<AuthResponseDto> response = new ApiResponseDto<AuthResponseDto>("User login successfully", HttpStatus.ACCEPTED.value(), user);
		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
	
	/**
	 * This Java function handles registration requests by processing the provided data and returning a
	 * response with the registration status.
	 * 
	 * @param dto The `dto` parameter in the `register` method is of type `AuthRegisterRequestDto`. It is
	 * annotated with `@RequestBody`, indicating that the method expects the request body to be converted
	 * to this object. The `AuthRegisterRequestDto` class likely contains the necessary information for
	 * registering a user
	 * @return The method is returning a `ResponseEntity` object containing an `ApiResponseDto` object
	 * with a message "User registered successfully", an HTTP status code of 200 (OK), and a boolean value
	 * indicating whether the user was successfully registered.
	 */
	@PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> register(@RequestBody AuthRegisterRequestDto dto) {
		Boolean registered = detailsServiceImpl.register(dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("User registered successfully", HttpStatus.OK.value(), registered);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
