package com.accesodatos.mappers.bet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;
import com.accesodatos.entity.Bet;

@Mapper(componentModel = "spring")
public interface BetMapper {

	BetResponseDto toBetResponseDto(Bet bet);

	@Mapping(target = "betId", ignore = true)
	@Mapping(target = "match", ignore = true)
	@Mapping(target = "user", ignore = true)
	Bet toBet (BetRequestDto dto);
}