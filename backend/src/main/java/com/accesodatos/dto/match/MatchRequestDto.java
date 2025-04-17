package com.accesodatos.dto.match;

import java.sql.Date;
import java.util.Set;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchRequestDto {

	private Date date;
	private Long competitionId;
	private Set<Long> teamIds;
}
