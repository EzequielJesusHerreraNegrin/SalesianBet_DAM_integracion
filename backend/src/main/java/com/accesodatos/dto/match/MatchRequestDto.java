package com.accesodatos.dto.match;

import java.sql.Date;
import java.util.Set;

import com.accesodatos.dto.teamdto.TeamResponseDto;
import com.accesodatos.entity.Competition;
import com.accesodatos.entity.Team;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchRequestDto {

	private Date date;
	private Competition competition;
	private Set<Team> teams;
}
