package com.accesodatos.mappers.userentity;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.userentity.UserEntityRegisterRequestDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

	UserEntityResponseDto toUserEntityResponseDto(UserEntity userEntity);

	@Mapping(target = "userId", ignore = true)
	@Mapping(target = "basket", ignore = true)
	@Mapping(target = "purchases", ignore = true)
	@Mapping(target = "bets", ignore = true)
	@Mapping(target = "points", ignore = true)
	@Mapping(target = "roles", ignore = true)
	UserEntity toUserEntity(UserEntityRegisterRequestDto dto);

	Set<UserEntityResponseDto> toUserResponseDtoSet(Set<UserEntity> users); // Para mappear la lista de usuarios del
																			// producto en el carrito.
}
