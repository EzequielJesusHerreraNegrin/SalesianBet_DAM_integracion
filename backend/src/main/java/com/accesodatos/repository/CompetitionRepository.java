package com.accesodatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Competition;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
	
	Competition findByCountry(String country);
}
