package com.accesodatos.dto.bet;

import lombok.Data;

@Data
public class BetRequestDto {

	private String prediction;
	private int points;
	private Long matchId;
	private Long userId;
}
