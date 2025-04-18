package com.accesodatos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Bet;

@Repository
public interface BetRepository extends JpaRepository<Bet, Long> {

	Optional<List<Bet>> findByUserEmail(String email);
	
}
