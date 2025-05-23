
package com.accesodatos.mappers.match;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.match.MatchRequestDto;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.entity.Match;

@Mapper(componentModel = "spring")
public interface MatchMapper {

	@Mapping(target = "matchId", ignore = true)
	@Mapping(target = "playing", ignore = true)
	@Mapping(target = "competition", ignore = true)
	@Mapping(target = "homeTeam", ignore = true)
	@Mapping(target = "awayTeam", ignore = true)
	@Mapping(target = "bets", ignore = true)
	Match toMatch(MatchRequestDto matchRequestDto);

	MatchResponseDto toMatchResponseDto(Match match);

}