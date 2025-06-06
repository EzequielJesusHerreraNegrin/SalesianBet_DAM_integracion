package com.accesodatos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Bet;
import com.accesodatos.entity.Match;
import com.accesodatos.entity.UserEntity;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

	List<Bet> findBetsByUserUserId(Long userId);
	
	boolean existsByUserAndMatch(UserEntity user, Match match);
	
	List<Bet> findByMatch(Match match);
	
	Bet findByUserAndMatch(UserEntity user, Match match);
}
