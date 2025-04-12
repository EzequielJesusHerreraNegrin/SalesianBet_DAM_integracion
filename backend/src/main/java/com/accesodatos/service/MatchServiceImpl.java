package com.accesodatos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchRequestUpdateDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.entity.Competition;
import com.accesodatos.entity.Match;
import com.accesodatos.entity.Team;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.match.MatchMapper;
import com.accesodatos.repository.CompetitionRepository;
import com.accesodatos.repository.MatchRepository;

@Service
public class MatchServiceImpl implements MatchService {

	private static final String MATCH_NOT_FOUND = "Match with id &d not found";
	private static final String COMPETITION_NOT_FOUND = "Competition with id &d not found";

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	MatchMapper matchMapper;

	@Autowired
	CompetitionRepository competitionRepository;

	private Match validateAndGetMatch(Long id) {
		return matchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(MATCH_NOT_FOUND, id)));
	}

	private Competition validateAndGetCompetition(Long id) {
		return competitionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(COMPETITION_NOT_FOUND, id)));
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
		Match createdMatch = matchMapper.toMatch(matchRequestDto);
		return matchMapper.toMatchResponseDto(createdMatch);
	}

	@Override
	public MatchResponseDto updateMatch(Long matchId, MatchRequestUpdateDto matchRequestUpdateDto) {
		Match updatedMatch = validateAndGetMatch(matchId);
		updatedMatch.setDate(matchRequestUpdateDto.getDate());
		updatedMatch.setCompetition(matchRequestUpdateDto.getCompetition());
		updatedMatch.setTeams(matchRequestUpdateDto.getTeams());
		updatedMatch.setResult(matchRequestUpdateDto.getResult());

		matchRepository.save(updatedMatch);

		return matchMapper.toMatchResponseDto(updatedMatch);
	}

	@Override
	public void deleteMatch(Long matchId) {
		Match match = validateAndGetMatch(matchId);
		matchRepository.delete(match);
	}

	@Override
	public List<MatchResponseDto> getMatchesByCompetition(Long competitionId) {
		Competition competition = validateAndGetCompetition(competitionId);
		List<MatchResponseDto> matches = matchRepository.findByCompetition(competition).stream()
				.map(matchMapper::toMatchResponseDto)
				.collect(Collectors.toList());

		return matches;
	}

	@Override
	public List<MatchResponseDto> getMatchesByDate(LocalDate date) {
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(LocalTime.MAX);
		List<MatchResponseDto> matches = matchRepository.findByDateBetweenOrderByDateAsc(start, end).stream()
				.map(matchMapper::toMatchResponseDto).collect(Collectors.toList());
		
		return matches;
	}

}
