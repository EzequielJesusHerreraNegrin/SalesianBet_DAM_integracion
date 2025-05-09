package com.accesodatos.service;

import java.time.LocalDate;
import java.util.List;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.dto.match.MatchResultRequestDto;

public interface MatchService {

	List<MatchResponseDto> getAllMatches();

	MatchResponseDto getMatchById(Long matchId);

	MatchResponseDto createMatch(MatchRequestDto matchRequestDto);

	MatchResponseDto updateMatch(Long matchId, MatchRequestDto matchRequestDto);

	void deleteMatch(Long matchId);

	List<MatchResponseDto> getMatchesByDateOrderByCompetition(LocalDate date);

	void updateIsPlayingMatch();

	List<MatchResponseDto> getMatchesReadyToValidate();

	MatchResponseDto validateMatch(Long matchId, MatchResultRequestDto MatchResult);
}	
