package com.accesodatos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findByDateBetweenOrderByCompetitionNameAsc(LocalDateTime start, LocalDateTime end);
}
