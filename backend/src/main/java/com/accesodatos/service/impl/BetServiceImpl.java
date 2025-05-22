package com.accesodatos.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;
import com.accesodatos.entity.Bet;
import com.accesodatos.entity.Match;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.bet.BetMapper;
import com.accesodatos.mappers.match.MatchMapper;
import com.accesodatos.repository.BetRepository;
import com.accesodatos.repository.MatchRepository;
import com.accesodatos.repository.UserEntityRepository;
import com.accesodatos.service.BetService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BetServiceImpl implements BetService {

	private static final String BET_NOT_FOUND = "Bet with id %d not found";
	private static final String MATCH_NOT_FOUND = "Match with id %d not found";
	private static final String USER_NOT_FOUND = "User with id %d not found";

	@Autowired
	BetRepository betRepository;

	@Autowired
	BetMapper betMapper;

	@Autowired
	MatchMapper matchMapper;

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	UserEntityRepository userEntityRepository;

	private Bet validateAndGetBet(Long id) {
		return betRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(BET_NOT_FOUND, id)));
	}

	private Match validateAndGetMatch(Long id) {
		return matchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(MATCH_NOT_FOUND, id)));
	}

	private UserEntity validateAndGetUser(Long id) {
		return userEntityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
	}

	@Override
	public List<BetResponseDto> getAllBets() {
		List<Bet> bets = betRepository.findAll();
		return bets.stream().map(betMapper::toBetResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<BetResponseDto> getBetByUserEmail(String email) {
		List<Bet> bets = betRepository.findByUserEmail(email).orElseThrow(
				() -> new ResourceNotFoundException(String.format("The email: %s, was not found.", email)));
		return bets.stream().map(betMapper::toBetResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public BetResponseDto createBet(BetRequestDto dto) {
		Match match = validateAndGetMatch(dto.getMatchId());

		UserEntity user = validateAndGetUser(dto.getUserId());

		if (betRepository.existsByUserAndMatch(user, match)) {
			throw new IllegalArgumentException(user.getUserName() + " ya habías apostado antes a este partido");
		}

		if (match.getDate().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("No puedes hacer la apuesta, porque el partido ya ha empezado");
		}

		if (dto.getPoints() <= 0) {
			throw new IllegalArgumentException("Debes apostar más de 0 puntos");
		}

		if (user.getPoints() < dto.getPoints()) {
			throw new IllegalArgumentException("No tienes los puntos suficientes");
		}

		user.setPoints(user.getPoints() - dto.getPoints());

		userEntityRepository.save(user);

		Bet bet = new Bet();
		bet.setPrediction(dto.getPrediction());
		bet.setPoints(dto.getPoints());
		bet.setMatch(match);
		bet.setUser(user);

		bet = betRepository.save(bet);
		return betMapper.toBetResponseDto(bet);

	}

	@Override
	public BetResponseDto updateBetById(Long betId, BetRequestDto betRequestDto) {
		Bet bet = validateAndGetBet(betId);

		Match match = validateAndGetMatch(betRequestDto.getMatchId());

		UserEntity user = bet.getUser();
		
		if (match.getDate().isBefore(LocalDateTime.now())) {
			new IllegalArgumentException("You cant edit this bet, because match has already begin");
		}

		int originalPoints = bet.getPoints();
		int newPoints = betRequestDto.getPoints();
		int difference = newPoints - originalPoints; 
		
		if (difference > 0) {
			if (user.getPoints() < difference) {
				throw new IllegalArgumentException("No tienes suficientes puntos para aumentar esta apuesta.");
			}
			user.setPoints(user.getPoints() - difference);
		} else if (difference < 0) {
			user.setPoints(user.getPoints() + difference);
		}
		
		bet.setPrediction(betRequestDto.getPrediction());
		bet.setPoints(betRequestDto.getPoints());
		userEntityRepository.save(user);
		bet = betRepository.save(bet);

		return betMapper.toBetResponseDto(bet);
	}

	@Override
	public void deleteBet(Long betId) {
		Bet bet = validateAndGetBet(betId);
		UserEntity user = validateAndGetUser(bet.getUser().getUserId());
		if (bet.getMatch().getDate().isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("No puedes borrar la apuesta de este partido");
		}
		
		bet.getUser().removeBet(bet);
		user.setPoints(user.getPoints() + bet.getPoints());
		userEntityRepository.save(user);
		betRepository.delete(bet);
	}

	@Override
	public BetResponseDto getBetByUserIdAndByMatchId(Long userId, Long matchId) {
		UserEntity user = validateAndGetUser(userId);
		Match match = validateAndGetMatch(matchId);
		
		Bet bet = betRepository.findByUserAndMatch(user, match);
		return betMapper.toBetResponseDto(bet);
	}

}
