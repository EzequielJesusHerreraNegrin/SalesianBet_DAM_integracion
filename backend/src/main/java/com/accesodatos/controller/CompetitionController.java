package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.accesodatos.dto.competitiondto.CompetitionRequestDto;
import com.accesodatos.dto.teamdto.TeamRequestDto;
import com.accesodatos.dto.teamdto.TeamResponseDto;
import com.accesodatos.entity.Competition;
import com.accesodatos.service.CompetitionServiceImpl;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/v1")
public class CompetitionController {

	private static final String COMPETITION_RESOURCE = "/competitions";
	private static final String COMPETITION_PATH_ID = COMPETITION_RESOURCE + "/{competitionId}";
	
	@Autowired
	CompetitionServiceImpl competitionService;
	
	@GetMapping(value = COMPETITION_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<Competition>>> getAllCompetitions() {
		List<Competition> competitions = competitionService.getAllCompetitions();
		
		ApiResponseDto<List<Competition>> response = new ApiResponseDto<>("Competitions fetched successfully",
				HttpStatus.OK.value(), competitions);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping(value = COMPETITION_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Competition>> getCompetitionById(@PathVariable Long competitionId) {
		Competition competition = competitionService.getCompetitionById(competitionId);
		
		ApiResponseDto<Competition> response = new ApiResponseDto<>("Competition fetched successfully",
				HttpStatus.OK.value(), competition);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	
	@PostMapping(value = COMPETITION_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Competition>> creteTeam(@RequestBody CompetitionRequestDto competitionRequestDto) {
		Competition competition = competitionService.createCompetition(competitionRequestDto);
		
		ApiResponseDto<Competition> response = new ApiResponseDto<>("Competition created successfully",
				HttpStatus.CREATED.value(), competition);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping(value = COMPETITION_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Competition>> updateTeam(@PathVariable Long competitionId, @RequestBody CompetitionRequestDto competitionRequestDto) {
		Competition competition = competitionService.updateCompetition(competitionId, competitionRequestDto);
		
		ApiResponseDto<Competition> response= new ApiResponseDto<>("Competition updated successfully",
				HttpStatus.OK.value(), competition);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = COMPETITION_PATH_ID)
	public ResponseEntity<Void> deleteTeam(@PathVariable Long competitionId){
		competitionService.deleteCompetition(competitionId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
