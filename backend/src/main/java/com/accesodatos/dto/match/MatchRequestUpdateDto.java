package com.accesodatos.dto.match;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Set;

import com.accesodatos.entity.Competition;
import com.accesodatos.entity.Team;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchRequestUpdateDto {

	private LocalDateTime date;
	private Long competitionId;
	private Set<Long> teamIds;
	private String result;
	
}
