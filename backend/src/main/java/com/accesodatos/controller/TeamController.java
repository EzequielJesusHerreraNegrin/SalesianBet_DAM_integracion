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
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class TeamController {

	private static final String TEAM_RESOURCE = "/teams";
	private static final String TEAM_PATH_ID = TEAM_RESOURCE + "/{teamId}";
	
	@Autowired
	TeamServiceImpl teamService;
	

	/**
	 * This Java function retrieves all teams and returns them as a JSON response with an appropriate
	 * status code.
	 * 
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a list of
	 * TeamResponseDto objects. The ApiResponseDto includes a success message, HTTP status code, and the
	 * list of teams fetched from the teamService.
	 */
	@GetMapping(value = TEAM_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<TeamResponseDto>>> getAllTeams() {
		List<TeamResponseDto> teams = teamService.getAllTeams();
		
		ApiResponseDto<List<TeamResponseDto>> response = new ApiResponseDto<>("Teams fetched successfully",
				HttpStatus.OK.value(), teams);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function retrieves a team by its ID and returns a response containing the team
	 * information in JSON format.
	 * 
	 * @param teamId The `teamId` parameter is a `Long` value representing the unique identifier of the
	 * team that you want to retrieve information for.
	 * @return The method `getTeamById` is returning a `ResponseEntity` containing an `ApiResponseDto`
	 * with a message indicating that the team was fetched successfully, an HTTP status code of 200 (OK),
	 * and the `TeamResponseDto` object representing the team data.
	 */
	@GetMapping(value = TEAM_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<TeamResponseDto>> getTeamById(@PathVariable Long teamId) {
		TeamResponseDto team = teamService.getTeamById(teamId);
		
		ApiResponseDto<TeamResponseDto> response = new ApiResponseDto<>("Team fetched successfully",
				HttpStatus.OK.value(), team);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This Java function creates a new team by handling a POST request with JSON data and returns a
	 * response with the created team details.
	 * 
	 * @param teamRequestDto The `teamRequestDto` parameter in the `creteTeam` method is of type
	 * `TeamRequestDto`. It is a request data transfer object that contains the information needed to
	 * create a new team. This object is received as JSON in the request body when the endpoint is called.
	 * @return The method is returning a `ResponseEntity` object containing an `ApiResponseDto` with a
	 * success message, HTTP status code 201 (Created), and the created `TeamResponseDto` object.
	 */
	@PostMapping(value = TEAM_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<TeamResponseDto>> creteTeam(@RequestBody TeamRequestDto teamRequestDto) {
		TeamResponseDto team = teamService.createTeam(teamRequestDto);
		
		ApiResponseDto<TeamResponseDto> response = new ApiResponseDto<>("Team created successfully",
				HttpStatus.CREATED.value(), team);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * This function updates a team with the provided teamId and request data, returning a response with
	 * the updated team information.
	 * 
	 * @param teamId The `teamId` parameter is a `Long` variable representing the unique identifier of the
	 * team that needs to be updated.
	 * @param teamRequestDto The `teamRequestDto` parameter in the `updateTeam` method is of type
	 * `TeamRequestDto`. It is used to pass the data necessary to update a team. This data typically
	 * includes information such as the team's name, members, description, or any other details that need
	 * to be modified
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a success
	 * message, HTTP status code, and the updated TeamResponseDto object.
	 */
	@PutMapping(value = TEAM_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<TeamResponseDto>> updateTeam(@PathVariable Long teamId, @RequestBody TeamRequestDto teamRequestDto) {
		TeamResponseDto team = teamService.updateTeam(teamId, teamRequestDto);
		
		ApiResponseDto<TeamResponseDto> response= new ApiResponseDto<>("Team updated successfully",
				HttpStatus.OK.value(), team);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This Java function uses a DELETE request to delete a team by its ID and returns a response with a
	 * status of NO_CONTENT.
	 * 
	 * @param teamId The `teamId` parameter is a `Long` value that is extracted from the path variable in
	 * the URL. It is used to uniquely identify the team that needs to be deleted from the system.
	 * @return The method is returning a ResponseEntity with a status of HttpStatus.NO_CONTENT.
	 */
	@DeleteMapping(value = TEAM_PATH_ID)
	public ResponseEntity<Void> deleteTeam(@PathVariable Long teamId){
		teamService.deleteTeam(teamId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
