package com.accesodatos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchRequestUpdateDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.service.MatchServiceImpl;

@RestController
@RequestMapping("/api/v1")
public class MatchController {

	private static final String MATCH_RESOURCE = "/matches";
	private static final String MATCH_PATH_ID = MATCH_RESOURCE + "/{matchId}";
	
	@Autowired
	MatchServiceImpl matchService;
	
	@GetMapping(value = MATCH_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<MatchResponseDto>>> getAllMatches() {
		List<MatchResponseDto> matches = matchService.getAllMatches();
		
		ApiResponseDto<List<MatchResponseDto>> response = new ApiResponseDto<>("Matches fetched successfully",
				HttpStatus.OK.value(), matches);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = MATCH_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> getMatchById(@PathVariable Long matchId) {
		MatchResponseDto match = matchService.getMatchById(matchId);
		
		ApiResponseDto<MatchResponseDto> response = new ApiResponseDto<>("Match fetched successfully",
				HttpStatus.OK.value(), match);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = MATCH_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> creteMatch(@RequestBody MatchRequestDto matchRequestDto) {
		MatchResponseDto createdMatch = matchService.createMatch(matchRequestDto);
		
		ApiResponseDto<MatchResponseDto> response = new ApiResponseDto<>("Match created successfully",
				HttpStatus.CREATED.value(), createdMatch);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping(value = MATCH_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> updateMatch(@PathVariable Long matchId, @RequestBody MatchRequestUpdateDto matchRequestDto) {
		MatchResponseDto updatedMatch = matchService.updateMatch(matchId, matchRequestDto);
		
		ApiResponseDto<MatchResponseDto> response= new ApiResponseDto<>("Match updated successfully",
				HttpStatus.OK.value(), updatedMatch);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = MATCH_PATH_ID)
	public ResponseEntity<Void> deleteTeam(@PathVariable Long matchId){
		matchService.deleteMatch(matchId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = MATCH_RESOURCE + "/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<MatchResponseDto>>> getAllMatchesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		List<MatchResponseDto> matches = matchService.getMatchesByDate(date);
		
		ApiResponseDto<List<MatchResponseDto>> response = new ApiResponseDto<>("Matches by date fetched successfully",
				HttpStatus.OK.value(), matches);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
