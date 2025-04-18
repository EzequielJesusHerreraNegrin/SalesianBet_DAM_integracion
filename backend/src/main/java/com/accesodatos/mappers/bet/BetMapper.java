package com.accesodatos.mappers.bet;

import org.mapstruct.Mapper;

import com.accesodatos.dto.bet.BetRequestDto;
import com.accesodatos.dto.bet.BetResponseDto;
import com.accesodatos.entity.Bet;

@Mapper(componentModel = "spring")
public interface BetMapper {

	BetResponseDto toBetResponseDto (Bet bet);
	
	Bet toBet (BetRequestDto dto);
}
