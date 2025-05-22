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

	@Override
	public List<MatchResponseDto> getMatchesByDateOrderByCompetition(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);
		List<MatchResponseDto> matches = matchRepository.findByDateBetweenOrderByCompetitionNameAscDateAsc(start, end)
				.stream()
				.map(matchMapper::toMatchResponseDto).collect(Collectors.toList());

		return matches;
	}

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

	@Override
	public List<MatchResponseDto> getMatchesReadyToValidate() {
		return matchRepository.findReadyToValidate(LocalDateTime.now().minusMinutes(90)).stream()
				.map(matchMapper::toMatchResponseDto).collect(Collectors.toList());
	}

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
