package com.accesodatos.dto.competitiondto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompetitionResponseDto {

	private Long competitionId;
	private String name;
	private String country;
}
