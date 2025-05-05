package com.accesodatos.mappers.team;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.teamdto.TeamRequestDto;
import com.accesodatos.dto.teamdto.TeamResponseDto;
import com.accesodatos.entity.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {

	@Mapping(target = "teamId", ignore = true)
	@Mapping(target = "homeMatches", ignore = true)
	@Mapping(target = "awayMatches", ignore = true)
	Team toTeam(TeamRequestDto teamRequestDto);
	
	TeamResponseDto toTeamResponseDto(Team team);
}
