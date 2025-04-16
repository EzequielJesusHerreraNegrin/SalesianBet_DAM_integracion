package com.accesodatos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;
import com.accesodatos.entity.Bet;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.bet.BetMapper;
import com.accesodatos.mappers.match.MatchMapper;
import com.accesodatos.repository.BetRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BetServiceImpl implements BetService{
	
	@Autowired BetRepository betRepository;
	@Autowired BetMapper betMapper;
	@Autowired MatchMapper matchMapper;
	
	@Override
	public List<BetResponseDto> getAllBets() {
		List<Bet> bets = betRepository.findAll(); 
		return bets.stream().map(betMapper::toBetResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<BetResponseDto> getBetByUserEmail(String email) {
		List<Bet> bets = betRepository.findByUserEmail(email).
				orElseThrow(() -> new ResourceNotFoundException(String.format("The email: %t, was not found.", email)));
		return bets.stream().map(betMapper::toBetResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public boolean createBet(BetRequestDto dto) {
		Bet bet = new Bet();
		bet.setPrediction(dto.getPrediction());
		bet.setPoints(dto.getPoints());
		bet.setMatch(matchMapper.toMatch(dto.getMatch()));
		
		bet = betRepository.save(bet);
		return bet != null;
	}

	@Override
	public boolean updateBetById(Long betId, BetRequestDto dto) {
		Bet bet = betRepository.findById(betId).
				orElseThrow(() -> 
				new ResourceNotFoundException(
						String.format("The bet with the id %d was not found.", betId)));
		
		bet.setPrediction(dto.getPrediction());
		bet.setPoints(dto.getPoints());
		bet.setMatch(matchMapper.toMatch(dto.getMatch()));
		bet = betRepository.save(bet);
		return bet != null;
	}

	@Override
	public boolean deleteBet(Long betId) {
		Bet bet = betRepository.findById(betId).
				orElseThrow(() -> 
				new ResourceNotFoundException(
						String.format("The bet with the id %d was not found.", betId)));
		bet.getUser().removeBet(bet);
		betRepository.delete(bet);
		return bet != null;
	}

}
