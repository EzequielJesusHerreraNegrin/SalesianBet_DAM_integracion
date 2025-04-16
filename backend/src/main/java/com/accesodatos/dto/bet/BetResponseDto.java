package com.accesodatos.dto.bet;

import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;

import lombok.Data;

@Data
public class BetResponseDto {

	private Long betId;
	private int points;
	private String type;
	private String prediction;
	private MatchResponseDto match;
	private UserEntityResponseDto user;
}
