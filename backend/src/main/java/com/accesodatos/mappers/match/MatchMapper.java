package com.accesodatos.mappers.match;

import org.mapstruct.Mapper;
import com.accesodatos.dto.match.MatchResponseDto;
import com.accesodatos.entity.Match;

@Mapper(componentModel = "spring")
public interface MatchMapper {

	MatchResponseDto toMatchResponseDto(Match match);
}
