package com.accesodatos.dto.match;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchRequestDto {

	private LocalDateTime date;
	private String result;
	private Long competitionId;
	private Long homeTeamId;
	private Long awayTeamId;
}
