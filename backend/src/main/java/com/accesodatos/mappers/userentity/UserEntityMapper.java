package com.accesodatos.mappers.userentity;

import org.mapstruct.Mapper;

import com.accesodatos.dto.userentity.UserEntityRegisterRequestDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

	UserEntityResponseDto toUserEntityResponseDto(UserEntity userEntity);

	// para el registro de usuario
	UserEntity toUserEntity(UserEntityRegisterRequestDto dto);
}
