package com.accesodatos.dto.match;

import java.sql.Date;
import java.util.Set;

import com.accesodatos.dto.competitiondto.CompetitionRequestDto;
import com.accesodatos.dto.teamdto.TeamRequestDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchRequestDto {

	private Date date;
	private CompetitionRequestDto competition;
	private Set<TeamRequestDto> teams;
}
