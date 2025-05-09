package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;
import com.accesodatos.service.impl.BetServiceImpl;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class BetController {

	private static final String BET_RESOURCE = "/bets";
	private static final String BET_PATH_ID = BET_RESOURCE + "/{betId}";
	private static final String BET_PATH_EMAIL = BET_RESOURCE + "/email";

	@Autowired
	BetServiceImpl betServiceImpl;

	@GetMapping(value = BET_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<BetResponseDto>>> getAllBets() {
		List<BetResponseDto> bets = betServiceImpl.getAllBets();

		ApiResponseDto<List<BetResponseDto>> response = new ApiResponseDto<List<BetResponseDto>>(
				"All bets fetched successfuly.", HttpStatus.OK.value(), bets);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(value = BET_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<BetResponseDto>> createBet(@RequestBody BetRequestDto betRequestDto) {
		BetResponseDto bet = betServiceImpl.createBet(betRequestDto);

		ApiResponseDto<BetResponseDto> response = new ApiResponseDto<>("The bet was created successfuly.",
				HttpStatus.CREATED.value(), bet);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(value = BET_PATH_EMAIL, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<BetResponseDto>>> getBetsByUserEmail(@RequestParam String value) {
		List<BetResponseDto> bets = betServiceImpl.getBetByUserEmail(value);

		ApiResponseDto<List<BetResponseDto>> response = new ApiResponseDto<List<BetResponseDto>>(
				"All bets fetched successfuly.", HttpStatus.OK.value(), bets);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = BET_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<BetResponseDto>> updateBet(@PathVariable Long betId,
			@RequestBody BetRequestDto dto) {
		BetResponseDto bet = betServiceImpl.updateBetById(betId, dto);

		ApiResponseDto<BetResponseDto> response = new ApiResponseDto<>("The bet was created successfuly.",
				HttpStatus.OK.value(), bet);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = BET_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Void>> deleteBet(@PathVariable Long betId) {
		betServiceImpl.deleteBet(betId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
