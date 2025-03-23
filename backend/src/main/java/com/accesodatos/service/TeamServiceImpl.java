package com.accesodatos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.teamdto.TeamRequestDto;
import com.accesodatos.dto.teamdto.TeamResponseDto;
import com.accesodatos.entity.Team;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.team.TeamMapper;
import com.accesodatos.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

	private static final String TEAM_NOT_FOUND = "Team with id &d not found";
	
	@Autowired
	TeamRepository teamRepository;
	
	@Autowired
	TeamMapper teamMapper;
	
	private Team validateAndGetTeam(Long id) {
		return teamRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format(TEAM_NOT_FOUND, id)));
	}
	
	@Override
	public List<TeamResponseDto> getAllTeams() {
		List<TeamResponseDto> teams = teamRepository.findAll().stream().map(teamMapper::toTeamResponseDto)
											.collect(Collectors.toList());
		return teams;
	}

	@Override
	public TeamResponseDto getTeamById(Long teamId) {
		Team team = validateAndGetTeam(teamId);
		return teamMapper.toTeamResponseDto(team);
	}

	@Override
	public TeamResponseDto createTeam(TeamRequestDto teamRequestDto) {
		Team team = teamMapper.toTeam(teamRequestDto);
		Team createdTeam = teamRepository.save(team);
		return teamMapper.toTeamResponseDto(createdTeam);
	}

	@Override
	public TeamResponseDto updateTeam(Long teamId, TeamRequestDto teamRequestDto) {
		Team team = validateAndGetTeam(teamId);
		team.setCountry(teamRequestDto.getCountry());
		team.setSport(teamRequestDto.getSport());
		team.setTeamName(teamRequestDto.getTeamName());
		return teamMapper.toTeamResponseDto(team);
	}

	@Override
	public void deleteTeam(Long teamId) {
		Team team = validateAndGetTeam(teamId);
		teamRepository.delete(team);

	}

}
