package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.competitiondto.CompetitionRequestDto;
import com.accesodatos.entity.Competition;
import com.accesodatos.service.impl.CompetitionServiceImpl;

@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class CompetitionController {

	private static final String COMPETITION_RESOURCE = "/competitions";
	private static final String COMPETITION_PATH_ID = COMPETITION_RESOURCE + "/{competitionId}";
	
	@Autowired
	CompetitionServiceImpl competitionService;
	
	/**
	 * This Java function retrieves all competitions and returns them as a JSON response.
	 * 
	 * @return This method returns a ResponseEntity containing an ApiResponseDto with a message indicating
	 * that competitions were fetched successfully, an HTTP status code of 200 (OK), and a list of
	 * Competition objects.
	 */
	@GetMapping(value = COMPETITION_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<Competition>>> getAllCompetitions() {
		List<Competition> competitions = competitionService.getAllCompetitions();
		
		ApiResponseDto<List<Competition>> response = new ApiResponseDto<>("Competitions fetched successfully",
				HttpStatus.OK.value(), competitions);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This Java function retrieves a competition by its ID and returns it as a JSON response.
	 * 
	 * @param competitionId The `competitionId` is a path variable representing the unique identifier of a
	 * competition. It is used in the `getCompetitionById` method to retrieve a specific competition from
	 * the database based on this identifier.
	 * @return The `getCompetitionById` method returns a `ResponseEntity` containing an `ApiResponseDto`
	 * with a message indicating that the competition was fetched successfully, along with the HTTP status
	 * code and the competition object itself.
	 */
	@GetMapping(value = COMPETITION_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Competition>> getCompetitionById(@PathVariable Long competitionId) {
		Competition competition = competitionService.getCompetitionById(competitionId);
		
		ApiResponseDto<Competition> response = new ApiResponseDto<>("Competition fetched successfully",
				HttpStatus.OK.value(), competition);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * This Java function creates a new competition based on the provided request data and returns a
	 * response with the created competition.
	 * 
	 * @param competitionRequestDto The `competitionRequestDto` is a data transfer object (DTO) that
	 * contains the information needed to create a new competition. It is received as the request body in
	 * the POST request to create a new competition. The `CompetitionRequestDto` class likely contains
	 * fields such as competition name, start date
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a success
	 * message, HTTP status code 201 (Created), and the Competition object that was created.
	 */
	@PostMapping(value = COMPETITION_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Competition>> creteTeam(@RequestBody CompetitionRequestDto competitionRequestDto) {
		Competition competition = competitionService.createCompetition(competitionRequestDto);
		
		ApiResponseDto<Competition> response = new ApiResponseDto<>("Competition created successfully",
				HttpStatus.CREATED.value(), competition);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	/**
	 * This function updates a competition entity based on the provided competition ID and request data,
	 * returning a response with the updated competition information.
	 * 
	 * @param competitionId The `competitionId` is a path variable representing the unique identifier of
	 * the competition that needs to be updated. It is extracted from the URL path of the request.
	 * @param competitionRequestDto The `competitionRequestDto` parameter in the `updateTeam` method is of
	 * type `CompetitionRequestDto`. It is used to pass the data necessary to update a competition. This
	 * data is typically sent in the request body as JSON and contains information such as the competition
	 * name, start date, end
	 * @return The method is returning a `ResponseEntity` containing an `ApiResponseDto` with a message
	 * indicating that the competition was updated successfully, along with an HTTP status code of 200
	 * (OK) and the updated `Competition` object.
	 */
	@PutMapping(value = COMPETITION_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Competition>> updateTeam(@PathVariable Long competitionId, @RequestBody CompetitionRequestDto competitionRequestDto) {
		Competition competition = competitionService.updateCompetition(competitionId, competitionRequestDto);
		
		ApiResponseDto<Competition> response= new ApiResponseDto<>("Competition updated successfully",
				HttpStatus.OK.value(), competition);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * This Java function uses a DELETE request to delete a competition by its ID and returns a response
	 * with a status of NO_CONTENT.
	 * 
	 * @param competitionId The `competitionId` parameter is a Long value that is extracted from the path
	 * variable in the URL. It is used to identify the specific competition that needs to be deleted.
	 * @return The method is returning a `ResponseEntity` with a status of `HttpStatus.NO_CONTENT`.
	 */
	@DeleteMapping(value = COMPETITION_PATH_ID)
	public ResponseEntity<Void> deleteTeam(@PathVariable Long competitionId){
		competitionService.deleteCompetition(competitionId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
	
}
