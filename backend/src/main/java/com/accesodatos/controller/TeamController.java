package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.teamdto.TeamRequestDto;
import com.accesodatos.dto.teamdto.TeamResponseDto;
import com.accesodatos.service.impl.TeamServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

	private static final String TEAM_RESOURCE = "/teams";
	private static final String TEAM_PATH_ID = TEAM_RESOURCE + "/{teamId}";
	
	@Autowired
	TeamServiceImpl teamService;
	

	@GetMapping(value = TEAM_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<TeamResponseDto>>> getAllTeams() {
		List<TeamResponseDto> teams = teamService.getAllTeams();
		
		ApiResponseDto<List<TeamResponseDto>> response = new ApiResponseDto<>("Teams fetched successfully",
				HttpStatus.OK.value(), teams);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = TEAM_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<TeamResponseDto>> getTeamById(@PathVariable Long teamId) {
		TeamResponseDto team = teamService.getTeamById(teamId);
		
		ApiResponseDto<TeamResponseDto> response = new ApiResponseDto<>("Team fetched successfully",
				HttpStatus.OK.value(), team);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = TEAM_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<TeamResponseDto>> creteTeam(@RequestBody TeamRequestDto teamRequestDto) {
		TeamResponseDto team = teamService.createTeam(teamRequestDto);
		
		ApiResponseDto<TeamResponseDto> response = new ApiResponseDto<>("Team created successfully",
				HttpStatus.CREATED.value(), team);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping(value = TEAM_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<TeamResponseDto>> updateTeam(@PathVariable Long teamId, @RequestBody TeamRequestDto teamRequestDto) {
		TeamResponseDto team = teamService.updateTeam(teamId, teamRequestDto);
		
		ApiResponseDto<TeamResponseDto> response= new ApiResponseDto<>("Team updated successfully",
				HttpStatus.OK.value(), team);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = TEAM_PATH_ID)
	public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId){
		teamService.deleteTeam(teamId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
