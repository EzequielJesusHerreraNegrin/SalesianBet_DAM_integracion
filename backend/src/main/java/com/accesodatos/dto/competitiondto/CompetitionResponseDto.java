package com.accesodatos.dto.competitiondto;

import java.util.List;

import com.accesodatos.dto.teamdto.TeamResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompetitionResponseDto {

	private Long competitionId;
	private String name;
	private String country;
}
