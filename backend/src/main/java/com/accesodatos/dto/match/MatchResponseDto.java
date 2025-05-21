package com.accesodatos.dto.match;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import com.accesodatos.dto.competitiondto.CompetitionResponseDto;
import com.accesodatos.dto.teamdto.TeamResponseDto;
import com.accesodatos.entity.Bet;
import com.accesodatos.entity.Competition;
import com.accesodatos.entity.Team;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class MatchResponseDto {

	private Long matchId;
	private LocalDateTime date;
	private String result;
	private CompetitionResponseDto competition;
	private TeamResponseDto homeTeam;
	private TeamResponseDto awayTeam;
}
