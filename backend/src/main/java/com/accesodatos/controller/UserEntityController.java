package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.bet.BetResponseDto;
import com.accesodatos.dto.userentity.UserEntityRegisterRequestDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.service.BetServiceImpl;
import com.accesodatos.service.PurchaseServiceImpl;
import com.accesodatos.service.UserEntityServiceImpl;

@RestController
@RequestMapping("api/v1")
public class UserEntityController {

	private static final String USER_RESOURCE = "/users";
	private static final String USER_PATH_EMAIL = USER_RESOURCE + "/{email}";
	private static final String USER_CARTITEM_ID = USER_RESOURCE + "/purchase/{userId}";
	private static final String BET_RESOURCE = "/bets";
	private static final String BET_PATH_EMAIL = BET_RESOURCE + "/{email}";
	
	@Autowired BetServiceImpl betServiceImpl;
	@Autowired UserEntityServiceImpl userEntityServiceImpl;
	@Autowired PurchaseServiceImpl purchaseServiceImpl;
	
	@GetMapping( value = USER_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)

	public ResponseEntity<ApiResponseDto<List<UserEntityResponseDto>>> getAllUsers() {
		List<UserEntityResponseDto> users = userEntityServiceImpl.getAllUsers();

		ApiResponseDto<List<UserEntityResponseDto>> response = new ApiResponseDto<List<UserEntityResponseDto>>(
				"All users fetched successfuly.", HttpStatus.OK.value(), users);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = USER_PATH_EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<UserEntityResponseDto>> getUserByEmail(@RequestParam String email) {
		UserEntityResponseDto users = userEntityServiceImpl.getUserByEmail(email);

		ApiResponseDto<UserEntityResponseDto> response = new ApiResponseDto<UserEntityResponseDto>(
				"All users fetched successfuly.", HttpStatus.OK.value(), users);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = USER_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> createUser(@RequestBody UserEntityRegisterRequestDto dto) {
		boolean user = userEntityServiceImpl.createUser(dto);

		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("User created successfuly.", HttpStatus.CREATED.value(), user);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = BET_PATH_EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<BetResponseDto>>> getBetsByUserEmail(@RequestParam String email) {
		List<BetResponseDto> bets = betServiceImpl.getBetByUserEmail(email);

		ApiResponseDto<List<BetResponseDto>> response = new ApiResponseDto<List<BetResponseDto>>(
				"All bets fetched successfuly.", HttpStatus.OK.value(), bets);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = USER_CARTITEM_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> buyCartItems(@PathVariable Long userId) {
		
		Boolean purchased = purchaseServiceImpl.buyUserBasketProducts(userId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("Purchase has been processed successfuly.", HttpStatus.OK.value(), purchased);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
