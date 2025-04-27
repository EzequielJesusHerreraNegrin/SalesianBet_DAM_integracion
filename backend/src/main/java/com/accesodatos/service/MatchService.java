package com.accesodatos.service;

import java.time.LocalDate;
import java.util.List;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchResponseDto;

public interface MatchService {

	List<MatchResponseDto> getAllMatches();
	MatchResponseDto getMatchById(Long matchId);
	MatchResponseDto createMatch(MatchRequestDto matchRequestDto);
	MatchResponseDto updateMatch(Long matchId, MatchRequestDto matchRequestDto);
	void deleteMatch(Long matchId);
	List<MatchResponseDto> getMatchesByDateOrderByCompetition(LocalDate date);
	void updateIsPlayingMatch();
}
