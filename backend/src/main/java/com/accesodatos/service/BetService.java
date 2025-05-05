package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;

public interface BetService {
	List<BetResponseDto> getAllBets();
	List<BetResponseDto> getBetByUserEmail(String email);
	boolean createBet(BetRequestDto dto);
	boolean updateBetById(Long betId, BetRequestDto betRequestDto);
	boolean deleteBet(Long betId);	
	
}
