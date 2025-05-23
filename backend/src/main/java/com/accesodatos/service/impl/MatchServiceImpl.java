package com.accesodatos.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.dto.match.MatchResultRequestDto;
import com.accesodatos.entity.Bet;
import com.accesodatos.entity.Competition;
import com.accesodatos.entity.Match;
import com.accesodatos.entity.Team;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.match.MatchMapper;
import com.accesodatos.repository.BetRepository;
import com.accesodatos.repository.CompetitionRepository;
import com.accesodatos.repository.MatchRepository;
import com.accesodatos.repository.TeamRepository;
import com.accesodatos.repository.UserEntityRepository;
import com.accesodatos.service.MatchService;

@Service
public class MatchServiceImpl implements MatchService {

	private static final String MATCH_NOT_FOUND = "Match with id &d not found";
	private static final String COMPETITION_NOT_FOUND = "Competition with id &d not found";
	private static final String TEAM_NOT_FOUND = "Team with id &d not found";

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	MatchMapper matchMapper;

	@Autowired
	CompetitionRepository competitionRepository;

	@Autowired
	TeamRepository teamRepository;

	@Autowired
	BetRepository betRepository;

	@Autowired
	UserEntityRepository userRepository;

	private Match validateAndGetMatch(Long id) {
		return matchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(MATCH_NOT_FOUND, id)));
	}

	private Competition validateAndGetCompetition(Long id) {
		return competitionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(COMPETITION_NOT_FOUND, id)));
	}

	private Team validateAndGetTeam(Long id) {
		return teamRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(TEAM_NOT_FOUND, id)));
	}

	/**
	 * The function `calculatePrediction` determines the outcome of a match based on the number of goals
	 * scored by the home and away teams.
	 * 
	 * @param result The `calculatePrediction` method takes a string `result` as input, which represents
	 * the result of a football match in the format "homeGoals-awayGoals". For example, if the result is
	 * "2-1", it means the home team scored 2 goals and the away team scored 1
	 * @return The method `calculatePrediction` returns a String indicating the prediction based on the
	 * result of a match. The possible return values are "LOCAL" if the home team scored more goals,
	 * "VISITANTE" if the away team scored more goals, and "EMPATE" if both teams scored an equal number
	 * of goals.
	 */
	private String calculatePrediction(String result) {
		String[] goals = result.trim().split("-");
		int homeGoals = Integer.parseInt(goals[0]);
		int awayGoals = Integer.parseInt(goals[1]);

		if (homeGoals > awayGoals) {
			return "LOCAL";
		} else if (awayGoals > homeGoals) {
			return "VISITANTE";
		} else {
			return "EMPATE";
		}
	}

	@Override
	public List<MatchResponseDto> getAllMatches() {
		List<MatchResponseDto> matches = matchRepository.findAll().stream().map(matchMapper::toMatchResponseDto)
				.collect(Collectors.toList());
		return matches;
	}

	@Override
	public MatchResponseDto getMatchById(Long matchId) {
		Match match = validateAndGetMatch(matchId);
		return matchMapper.toMatchResponseDto(match);
	}

	/**
	 * The `createMatch` function in Java creates a new match based on the provided request data and saves
	 * it to the repository.
	 * 
	 * @param matchRequestDto The `matchRequestDto` parameter contains information needed to create a new
	 * match. It includes the following data:
	 * @return The method `createMatch` returns a `MatchResponseDto` object.
	 */
	@Override
	public MatchResponseDto createMatch(MatchRequestDto matchRequestDto) {
		System.out.println("Entro" + matchRequestDto);
		Competition competition = validateAndGetCompetition(matchRequestDto.getCompetitionId());

		Team homeTeam = validateAndGetTeam(matchRequestDto.getHomeTeamId());

		Team awayTeam = validateAndGetTeam(matchRequestDto.getAwayTeamId());

		Match createdMatch = new Match();

		createdMatch.setCompetition(competition);
		createdMatch.setDate(matchRequestDto.getDate());
		createdMatch.setHomeTeam(homeTeam);
		createdMatch.setAwayTeam(awayTeam);

		if (createdMatch.getPlaying() == null && createdMatch.getResult() == null) {
			createdMatch.setPlaying(false);
			createdMatch.setResult("");
		}

		Match savedMatch = matchRepository.save(createdMatch);

		return matchMapper.toMatchResponseDto(savedMatch);
	}

	/**
	 * This Java function updates a match entity with new details and saves it to the repository.
	 * 
	 * @param matchId The `matchId` parameter is the unique identifier of the match that you want to
	 * update. It is used to retrieve the specific match from the database that you want to modify.
	 * @param matchRequestDto The `matchRequestDto` parameter contains information about the match that
	 * needs to be updated. This information includes the competition ID, home team ID, away team ID,
	 * date, and result of the match.
	 * @return The method is returning a `MatchResponseDto` object.
	 */
	@Override
	public MatchResponseDto updateMatch(Long matchId, MatchRequestDto matchRequestDto) {
		Competition competition = validateAndGetCompetition(matchRequestDto.getCompetitionId());

		Team homeTeam = validateAndGetTeam(matchRequestDto.getHomeTeamId());

		Team awayTeam = validateAndGetTeam(matchRequestDto.getAwayTeamId());

		Match updatedMatch = validateAndGetMatch(matchId);
		
		if (updatedMatch.getDate().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("No puedes editar un partido ya comenzado");
		}
		
		updatedMatch.setDate(matchRequestDto.getDate());
		updatedMatch.setCompetition(competition);
		updatedMatch.setHomeTeam(homeTeam);
		updatedMatch.setAwayTeam(awayTeam);
		updatedMatch.setResult(matchRequestDto.getResult());

		matchRepository.save(updatedMatch);

		return matchMapper.toMatchResponseDto(updatedMatch);
	}

	@Override
	public void deleteMatch(Long matchId) {
		Match match = validateAndGetMatch(matchId);
		matchRepository.delete(match);
	}

	/**
	 * This Java function retrieves matches by date and orders them by competition name and date.
	 * 
	 * @param date The `date` parameter is a `LocalDate` object representing a specific date for which you
	 * want to retrieve matches.
	 * @return This method returns a list of `MatchResponseDto` objects, which are obtained by querying
	 * the `matchRepository` for matches that fall within the specified `date` range. The matches are then
	 * sorted by competition name in ascending order and date in ascending order before being mapped to
	 * `MatchResponseDto` objects.
	 */
	@Override
	public List<MatchResponseDto> getMatchesByDateOrderByCompetition(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);
		List<MatchResponseDto> matches = matchRepository.findByDateBetweenOrderByCompetitionNameAscDateAsc(start, end)
				.stream()
				.map(matchMapper::toMatchResponseDto).collect(Collectors.toList());

		return matches;
	}

	/**
	 * This function updates the `isPlaying` status of matches based on their start and end times,
	 * toggling the status if the current time falls within the match duration.
	 */
	@Override
	@Scheduled(fixedRate = 60000) // cada minuto
	public void updateIsPlayingMatch() {
		List<Match> matches = matchRepository.findAll();

		LocalDateTime now = LocalDateTime.now();

		for (Match match : matches) {
			LocalDateTime start = match.getDate();
			LocalDateTime end = start.plusMinutes(90);

			if (!match.getPlaying() && now.isAfter(start) && now.isBefore(end)) {
				match.setPlaying(true);
			}

			if (match.getPlaying() && now.isAfter(end)) {
				match.setPlaying(false);
			}
		}
		matchRepository.saveAll(matches);

	}

	/**
	 * This function retrieves a list of MatchResponseDto objects for matches that are ready to be
	 * validated.
	 * 
	 * @return A list of MatchResponseDto objects is being returned.
	 */
	@Override
	public List<MatchResponseDto> getMatchesReadyToValidate() {
		return matchRepository.findReadyToValidate(LocalDateTime.now().minusMinutes(90)).stream()
				.map(matchMapper::toMatchResponseDto).collect(Collectors.toList());
	}
/**
 * The function validates and sets the result of a match, updates user points based on correct
 * predictions, and returns a MatchResponseDto.
 * 
 * @param matchId The `matchId` parameter is the unique identifier of the match for which the result is
 * being validated. It is used to retrieve the match details from the database and perform validation
 * checks before setting the match result.
 * @param matchResult The `matchResult` parameter is of type `MatchResultRequestDto`, which likely
 * contains information about the result of a match. In the provided code snippet, this parameter is
 * used to update the result of a match entity and then calculate earnings for users who made correct
 * predictions on the match outcome.
 * @return The method `validateMatch` is returning a `MatchResponseDto` object.
 */

	@Override
	public MatchResponseDto validateMatch(Long matchId, MatchResultRequestDto matchResult) {

		Match match = validateAndGetMatch(matchId);

		if (match.getPlaying()) {
			throw new IllegalStateException("Cannot set result while match is still playing!");
		}

		if (match.getResult() != null && !match.getResult().isBlank()) {
			throw new IllegalStateException("Match already have a result");
		}

		match.setResult(matchResult.getResult());
		matchRepository.save(match);

		List<Bet> bets = betRepository.findByMatch(match);

		for (Bet bet : bets) {
			UserEntity user = bet.getUser();

			String realPrediction = calculatePrediction(matchResult.getResult());

			if (bet.getPrediction().equalsIgnoreCase(realPrediction)) {
				int earnings = bet.getPoints() * 2;
				user.setPoints(user.getPoints() + earnings);
				userRepository.save(user);
			}
		}

		return matchMapper.toMatchResponseDto(match);
	}

}
