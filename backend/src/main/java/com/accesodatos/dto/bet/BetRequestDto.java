package com.accesodatos.dto.bet;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchResponseDto;

import lombok.Data;

@Data
public class BetRequestDto {

	private String prediction;
	private int points;
	private MatchRequestDto match;
}
