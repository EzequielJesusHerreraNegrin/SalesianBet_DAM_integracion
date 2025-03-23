package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.teamdto.TeamRequestDto;
import com.accesodatos.dto.teamdto.TeamResponseDto;

public interface TeamService {

	List<TeamResponseDto> getAllTeams();
	TeamResponseDto getTeamById(Long teamId);
	TeamResponseDto createTeam(TeamRequestDto teamRequestDto);
	TeamResponseDto updateTeam(Long teamId, TeamRequestDto teamRequestDto);
	void deleteTeam(Long teamId);
	
}
