package com.accesodatos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchRequestUpdateDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.entity.Match;

public interface MatchService {

	List<MatchResponseDto> getAllMatches();
	MatchResponseDto getMatchById(Long matchId);
	MatchResponseDto createMatch(MatchRequestDto matchRequestDto);
	MatchResponseDto updateMatch(Long matchId, MatchRequestUpdateDto matchRequestUpdateDto);
	void deleteMatch(Long matchId);
	List<MatchResponseDto> getMatchesByCompetition(Long competitionId);
	List<MatchResponseDto> getMatchesByDate(LocalDate date);
	
}
