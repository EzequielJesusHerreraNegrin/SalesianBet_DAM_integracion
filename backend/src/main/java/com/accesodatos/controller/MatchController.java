package com.accesodatos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.service.impl.MatchServiceImpl;
import com.accesodatos.dto.match.MatchResultRequestDto;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class MatchController {

	private static final String MATCH_RESOURCE = "/matches";
	private static final String MATCH_PATH_ID = MATCH_RESOURCE + "/{matchId}";

	@Autowired
	MatchServiceImpl matchService;

	/**
	 * This Java function retrieves all matches and returns them as a JSON response.
	 * 
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto object with a
	 * list of MatchResponseDto objects. The ApiResponseDto object includes a success message, HTTP status
	 * code, and the list of matches fetched from the matchService.
	 */
	@GetMapping(value = MATCH_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<MatchResponseDto>>> getAllMatches() {
		List<MatchResponseDto> matches = matchService.getAllMatches();

		ApiResponseDto<List<MatchResponseDto>> response = new ApiResponseDto<>("Matches fetched successfully",
				HttpStatus.OK.value(), matches);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function retrieves a match by its ID and returns a JSON response containing the match
	 * details.
	 * 
	 * @param matchId The `matchId` parameter in the `getMatchById` method is a `Long` type variable
	 * representing the unique identifier of the match that is being requested. This method fetches the
	 * match details based on the provided `matchId` and returns a response containing the match
	 * information in a standardized format
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a success
	 * message, HTTP status code, and the MatchResponseDto object retrieved by calling the getMatchById
	 * method from the matchService.
	 */
	@GetMapping(value = MATCH_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> getMatchById(@PathVariable Long matchId) {
		MatchResponseDto match = matchService.getMatchById(matchId);

		ApiResponseDto<MatchResponseDto> response = new ApiResponseDto<>("Match fetched successfully",
				HttpStatus.OK.value(), match);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function creates a match by processing a match request and returns a response with the
	 * created match details.
	 * 
	 * @param matchRequestDto The `matchRequestDto` parameter in the `createMatch` method is of type
	 * `MatchRequestDto`. It is a request data transfer object (DTO) that contains the necessary
	 * information to create a new match. This object is received as the request body in JSON format when
	 * the endpoint is called.
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a success
	 * message, HTTP status code 201 (Created), and the created MatchResponseDto object.
	 */
	@PostMapping(value = MATCH_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> createMatch(@RequestBody MatchRequestDto matchRequestDto) {
		MatchResponseDto createdMatch = matchService.createMatch(matchRequestDto);

		ApiResponseDto<MatchResponseDto> response = new ApiResponseDto<>("Match created successfully",
				HttpStatus.CREATED.value(), createdMatch);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * This Java function updates a match entity and returns a response with the updated match details.
	 * 
	 * @param matchId The `matchId` parameter in the `updateMatch` method is a `Long` type variable
	 * representing the unique identifier of the match that needs to be updated.
	 * @param matchRequestDto The `matchRequestDto` parameter in the `updateMatch` method is of type
	 * `MatchRequestDto`. It is a request data transfer object (DTO) that contains the information needed
	 * to update a match. This object likely includes fields such as match details, scores, teams, and any
	 * other relevant
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a success
	 * message, HTTP status code, and the updated MatchResponseDto object.
	 */
	@PutMapping(value = MATCH_PATH_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> updateMatch(@PathVariable Long matchId,
			@RequestBody MatchRequestDto matchRequestDto) {
		MatchResponseDto updatedMatch = matchService.updateMatch(matchId, matchRequestDto);

		ApiResponseDto<MatchResponseDto> response = new ApiResponseDto<>("Match updated successfully",
				HttpStatus.OK.value(), updatedMatch);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

/**
 * This Java function uses a DELETE request to delete a match by its ID and returns a response with a
 * status of NO_CONTENT.
 * 
 * @param matchId The `matchId` parameter is a Long value representing the unique identifier of the
 * match that needs to be deleted.
 * @return The method is returning a ResponseEntity with a status of HttpStatus.NO_CONTENT.
 */
	@DeleteMapping(value = MATCH_PATH_ID)
	public ResponseEntity<Void> deleteTeam(@PathVariable Long matchId) {
		matchService.deleteMatch(matchId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * This Java function retrieves a list of matches by date and returns them in a JSON response.
	 * 
	 * @param date The `getAllMatchesByDate` method is a Spring `@GetMapping` method that retrieves a list
	 * of matches based on a specific date. The method accepts a `LocalDate` parameter named `date`, which
	 * is annotated with `@RequestParam` and `@DateTimeFormat(iso = DateTimeFormat.IS
	 * @return This method returns a ResponseEntity containing an ApiResponseDto with a list of
	 * MatchResponseDto objects. The ApiResponseDto includes a message indicating that the matches by date
	 * were fetched successfully, an HTTP status code of 200 (OK), and the list of MatchResponseDto
	 * objects obtained from the matchService.
	 */
	@GetMapping(value = MATCH_RESOURCE + "/by-date", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<MatchResponseDto>>> getAllMatchesByDate(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		List<MatchResponseDto> matches = matchService.getMatchesByDateOrderByCompetition(date);

		ApiResponseDto<List<MatchResponseDto>> response = new ApiResponseDto<>("Matches by date fetched successfully",
				HttpStatus.OK.value(), matches);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function retrieves all matches ready to be validated and returns them in a JSON response.
	 * 
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a list of
	 * MatchResponseDto objects. The ApiResponseDto includes a success message, HTTP status code, and the
	 * list of matches ready to validate.
	 */
	@GetMapping(value = MATCH_RESOURCE + "/ready", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<MatchResponseDto>>> getAllMatchesReadyToValidate() {
		List<MatchResponseDto> matches = matchService.getMatchesReadyToValidate();

		ApiResponseDto<List<MatchResponseDto>> response = new ApiResponseDto<>(
				"Matches ready to validate fetched successfully",
				HttpStatus.OK.value(), matches);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This function validates a match using the provided match ID and match result request data.
	 * 
	 * @param matchId The `matchId` parameter is a `Long` value that is extracted from the path variable
	 * in the URL. It represents the unique identifier of a match that needs to be validated.
	 * @param matchResultRequestDto The `matchResultRequestDto` parameter in the `validateMatch` method is
	 * of type `MatchResultRequestDto`. It is a request body parameter that contains the data needed to
	 * validate a match. This parameter is passed in the request body as JSON and is used by the
	 * `matchService` to
	 * @return The method `validateMatch` is returning a `ResponseEntity` object containing an
	 * `ApiResponseDto` with a message indicating that the match validation was successful, an HTTP status
	 * code of 200 (OK), and the validated match data in a `MatchResponseDto` object.
	 */
	@PutMapping(value = MATCH_PATH_ID
			+ "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<MatchResponseDto>> validateMatch(@PathVariable Long matchId,
			@RequestBody MatchResultRequestDto matchResultRequestDto) {
		MatchResponseDto validatedMatch = matchService.validateMatch(matchId, matchResultRequestDto);

		ApiResponseDto<MatchResponseDto> response = new ApiResponseDto<>("Match validation successfully",
				HttpStatus.OK.value(), validatedMatch);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
