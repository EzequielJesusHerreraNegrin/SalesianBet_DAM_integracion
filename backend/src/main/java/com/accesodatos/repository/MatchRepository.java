package com.accesodatos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findByDateBetweenOrderByCompetitionNameAscDateAsc(LocalDateTime start, LocalDateTime end);
	
	@Query("SELECT m FROM Match m WHERE m.is_playing = false AND m.date <= :ninetyMinutes AND (m.result IS NULL OR m.result = '')")
	List<Match> findReadyToValidate(@Param("ninetyMinutes") LocalDateTime ninetyMinutes);
}
