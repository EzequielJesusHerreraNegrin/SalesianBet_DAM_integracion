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

	@Autowired
	BetServiceImpl betServiceImpl;

	/**
	 * This Java function retrieves all bets and returns them in a ResponseEntity with an ApiResponseDto.
	 * 
	 * @return This method returns a ResponseEntity object containing an ApiResponseDto with a list of
	 * BetResponseDto objects. The ApiResponseDto includes a message indicating that all bets were fetched
	 * successfully, an HTTP status code of 200 (OK), and the list of BetResponseDto objects retrieved
	 * from the service.
	 */
	@GetMapping(value = BET_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<BetResponseDto>>> getAllBets() {
		List<BetResponseDto> bets = betServiceImpl.getAllBets();

		ApiResponseDto<List<BetResponseDto>> response = new ApiResponseDto<List<BetResponseDto>>(
				"All bets fetched successfuly.", HttpStatus.OK.value(), bets);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function creates a new bet based on the provided request data and returns a response with
	 * the created bet information.
	 * 
	 * @param betRequestDto The `betRequestDto` parameter in the `createBet` method is of type
	 * `BetRequestDto`. It is annotated with `@RequestBody`, indicating that the method expects the
	 * request body to be converted to this object. This parameter likely contains the data necessary to
	 * create a new bet, such as
	 * @return The method is returning a ResponseEntity object containing an ApiResponseDto with a success
	 * message, HTTP status code 201 (Created), and the BetResponseDto object representing the created
	 * bet.
	 */
	@PostMapping(value = BET_RESOURCE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<BetResponseDto>> createBet(@RequestBody BetRequestDto betRequestDto) {
		BetResponseDto bet = betServiceImpl.createBet(betRequestDto);

		ApiResponseDto<BetResponseDto> response = new ApiResponseDto<>("The bet was created successfuly.",
				HttpStatus.CREATED.value(), bet);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * This Java function retrieves a list of bets by user ID and returns them in a JSON response.
	 * 
	 * @param userId The `userId` parameter in the `getBetsByUserId` method represents the unique
	 * identifier of the user for whom you want to retrieve the bets. This method fetches a list of
	 * `BetResponseDto` objects associated with the specified user ID.
	 * @return This method returns a ResponseEntity object containing an ApiResponseDto with a list of
	 * BetResponseDto objects. The ApiResponseDto includes a message indicating that all bets were fetched
	 * successfully, an HTTP status code of 200 (OK), and the list of BetResponseDto objects retrieved for
	 * the specified userId.
	 */
	@GetMapping(value = BET_RESOURCE + "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<BetResponseDto>>> getBetsByUserId(@PathVariable Long userId) {
		List<BetResponseDto> bets = betServiceImpl.getBetsByUserId(userId);

		ApiResponseDto<List<BetResponseDto>> response = new ApiResponseDto<List<BetResponseDto>>(
				"All bets fetched successfuly.", HttpStatus.OK.value(), bets);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function updates a bet by its ID and returns a response with the updated bet information.
	 * 
	 * @param betId The `betId` parameter is a `Long` value that is obtained from the path variable in the
	 * URL. It represents the unique identifier of the bet that needs to be updated.
	 * @param dto The `dto` parameter in the `updateBet` method is of type `BetRequestDto`. It is
	 * annotated with `@RequestBody`, indicating that the data for this parameter will be obtained from
	 * the request body of the HTTP request. This parameter is used to pass the details of the bet that
	 * needs
	 * @return The method is returning a `ResponseEntity` containing an `ApiResponseDto` with a message
	 * indicating the success of the bet creation, an HTTP status code of 200 (OK), and the updated
	 * `BetResponseDto` object.
	 */
	@PutMapping(value = BET_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<BetResponseDto>> updateBet(@PathVariable Long betId,
			@RequestBody BetRequestDto dto) {
		BetResponseDto bet = betServiceImpl.updateBetById(betId, dto);

		ApiResponseDto<BetResponseDto> response = new ApiResponseDto<>("The bet was created successfuly.",
				HttpStatus.OK.value(), bet);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * This Java function uses a DELETE request to delete a bet with a specific ID and returns a response
	 * with a status of NO_CONTENT.
	 * 
	 * @param betId The `betId` parameter is a `Long` value that is extracted from the path variable in
	 * the URL. It is used to uniquely identify the bet that needs to be deleted.
	 * @return A `ResponseEntity` with a status of `HttpStatus.NO_CONTENT` is being returned.
	 */
	@DeleteMapping(value = BET_PATH_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> deleteBet(@PathVariable Long betId) {
		betServiceImpl.deleteBet(betId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * This function retrieves a bet by user ID and match ID and returns it in a response entity.
	 * 
	 * @param userId The `userId` parameter in the `getBetByUserAndMatch` method represents the unique
	 * identifier of the user for whom you want to retrieve the bet information.
	 * @param matchId The `matchId` parameter in the `@GetMapping` method represents the unique identifier
	 * of the match for which you want to retrieve the bet information. It is used in the URL path to
	 * specify the specific match for which the bet is being fetched.
	 * @return The `getBetByUserAndMatch` method returns a ResponseEntity containing an ApiResponseDto
	 * with a message indicating that the bet by user and match was fetched successfully, along with the
	 * HTTP status code 200 (OK) and the BetResponseDto object representing the fetched bet.
	 */
	@GetMapping(value = BET_RESOURCE + "/users/{userId}/matches/{matchId}")
	public ResponseEntity<ApiResponseDto<BetResponseDto>> getBetByUserAndMatch(@PathVariable Long userId, @PathVariable Long matchId) {
		BetResponseDto bet = betServiceImpl.getBetByUserIdAndByMatchId(userId, matchId);
		
		ApiResponseDto<BetResponseDto> response = new ApiResponseDto<>("The bet by user and match fetched successfully",
				HttpStatus.OK.value(), bet);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}
