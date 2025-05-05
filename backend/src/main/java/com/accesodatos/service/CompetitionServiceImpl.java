package com.accesodatos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.competitiondto.CompetitionRequestDto;
import com.accesodatos.entity.Competition;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.competition.CompetitionMapper;
import com.accesodatos.repository.CompetitionRepository;

@Service
public class CompetitionServiceImpl implements CompetitionService {
	
	private static final String COMPETITION_NOT_FOUND = "Competition with id &d not found";
	
	@Autowired
	CompetitionRepository competitionRepository;
	
	@Autowired
	CompetitionMapper competitionMapper;
	private Competition validateAndGetCompetition(Long id) {
		return competitionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(COMPETITION_NOT_FOUND, id)));
	}
	
	@Override
	public List<Competition> getAllCompetitions() {
		return competitionRepository.findAll();
	}

	@Override
	public Competition getCompetitionById(Long competitionId) {
		Competition competition = validateAndGetCompetition(competitionId);
		return competition;
	}

	@Override
	public Competition createCompetition(CompetitionRequestDto competitionRequestDto) {
		Competition competition = competitionMapper.ToCompetition(competitionRequestDto);
		return competitionRepository.save(competition);
	}

	@Override
	public Competition updateCompetition(Long competitionId, CompetitionRequestDto competitionRequestDto) {
		Competition updatedCompetition = validateAndGetCompetition(competitionId);
		updatedCompetition.setName(competitionRequestDto.getName());
		updatedCompetition.setCountry(competitionRequestDto.getCountry());
		
		competitionRepository.save(updatedCompetition);
		return updatedCompetition;
	}

	@Override
	public void deleteCompetition(Long competitionId) {
		Competition competition = validateAndGetCompetition(competitionId);
		competitionRepository.delete(competition);

	}

	@Override
	public Competition getCompetitionByCountry(String country) {
		
		return competitionRepository.findByCountry(country);
	}

}
