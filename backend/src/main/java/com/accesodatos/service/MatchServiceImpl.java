package com.accesodatos.service;

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
import com.accesodatos.entity.Competition;
import com.accesodatos.entity.Match;
import com.accesodatos.entity.Team;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.match.MatchMapper;
import com.accesodatos.repository.CompetitionRepository;
import com.accesodatos.repository.MatchRepository;
import com.accesodatos.repository.TeamRepository;

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
		Competition competition = validateAndGetCompetition(matchRequestDto.getCompetitionId());
		
		Team homeTeam = validateAndGetTeam(matchRequestDto.getHomeTeamId());

		Team awayTeam = validateAndGetTeam(matchRequestDto.getAwayTeamId());
		
		Match createdMatch = new Match();
		
		createdMatch.setCompetition(competition);
		createdMatch.setDate(matchRequestDto.getDate());
		createdMatch.setHomeTeam(homeTeam);
		createdMatch.setAwayTeam(awayTeam);
		
		if (createdMatch.getIs_playing() == null && createdMatch.getResult() == null) {
			createdMatch.setIs_playing(false);
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
		List<MatchResponseDto> matches = matchRepository.findByDateBetweenOrderByCompetitionNameAscDateAsc(start, end).stream()
				.map(matchMapper::toMatchResponseDto).collect(Collectors.toList());
		
		return matches;
	}

	@Override
	@Scheduled(fixedRate = 60000) // cada minuto
	public void updateIsPlayingMatch() {
		List<Match> matches = matchRepository.findAll();
		
		LocalDateTime now = LocalDateTime.now();
		
		for (Match match : matches) {
			LocalDateTime start =  match.getDate();
			LocalDateTime end = start.plusMinutes(90);
			
			if (!match.getIs_playing() && now.isAfter(start) && now.isBefore(end)) {
				match.setIs_playing(true);
			}
			
			if (match.getIs_playing() && now.isAfter(end)) {
				match.setIs_playing(false);
			}
		}
		matchRepository.saveAll(matches);
		
	}

}
