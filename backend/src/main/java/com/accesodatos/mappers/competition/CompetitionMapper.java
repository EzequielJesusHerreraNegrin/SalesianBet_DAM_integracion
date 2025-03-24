package com.accesodatos.mappers.competition;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.competitiondto.CompetitionRequestDto;
import com.accesodatos.entity.Competition;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

	@Mapping(target = "competitionId", ignore = true)
	@Mapping(target = "matches", ignore = true)
	Competition ToCompetition(CompetitionRequestDto competitionRequestDto);
	
}
