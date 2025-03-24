package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.competitiondto.CompetitionRequestDto;
import com.accesodatos.entity.Competition;

public interface CompetitionService {

	List<Competition> getAllCompetitions();
	Competition getCompetitionById(Long competitionId);
	Competition createCompetition(CompetitionRequestDto competitionRequestDto);
	Competition updateCompetition(Long competitionId, CompetitionRequestDto competitionRequestDto);
	void deleteCompetition(Long competitionId);
	Competition getCompetitionByCountry(String country);
	
}
