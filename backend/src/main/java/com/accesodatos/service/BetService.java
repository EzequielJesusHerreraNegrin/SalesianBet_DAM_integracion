package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;

public interface BetService {
	List<BetResponseDto> getAllBets();
	List<BetResponseDto> getBetsByUserId(Long userId);
	BetResponseDto createBet(BetRequestDto dto);
	BetResponseDto updateBetById(Long betId, BetRequestDto betRequestDto);
	void deleteBet(Long betId);	
	BetResponseDto getBetByUserIdAndByMatchId(Long userId, Long matchId);
	
}
